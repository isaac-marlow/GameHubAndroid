package com.ilhomsoliev.gamehubandroid.feature.search.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ilhomsoliev.gamehubandroid.feature.game.data.GameRepository
import com.ilhomsoliev.gamehubandroid.feature.game.domain.GameModel
import com.ilhomsoliev.gamehubandroid.feature.library.data.LibraryRepository
import com.ilhomsoliev.gamehubandroid.feature.search.data.FiltersRepository
import com.ilhomsoliev.gamehubandroid.feature.search.data.GameSortOption
import com.ilhomsoliev.gamehubandroid.feature.search.data.SearchRepository
import com.ilhomsoliev.gamehubandroid.core.result.Result
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers

class SearchViewModel(
  private val repository: SearchRepository,
  private val filtersRepository: FiltersRepository,
  private val gameRepository: GameRepository,
  private val libraryRepository: LibraryRepository,
) : ViewModel() {

  private val _uiState = MutableStateFlow(SearchUiState())
  val uiState = _uiState.asStateFlow()

  private val searchQueryFlow = MutableStateFlow("")
  private val genreIdFlow = MutableStateFlow<Int?>(null)
  private val sortOptionFlow = MutableStateFlow(GameSortOption.RELEVANCE)
  private val releaseDatePeriodFlow = MutableStateFlow(ReleaseDatePeriod.ANY)
  private var currentPage = 1
  private var nextPageUrl: String? = null
  private var isPaging = false
  private var trendingPage = 1
  private var trendingNextUrl: String? = null
  private var isPagingTrending = false

  init {
    loadQuickFilters()
    loadTrendingGames()
    loadSavedGameIds()
    viewModelScope.launch {
      val debouncedQueryFlow = searchQueryFlow
        .debounce(400)
        .distinctUntilChanged()

      combine(
        debouncedQueryFlow,
        genreIdFlow,
        sortOptionFlow,
        releaseDatePeriodFlow
      ) { query, genreId, sortOption, datePeriod ->
        SearchRequest(query, genreId, sortOption, datePeriod)
      }.collectLatest { request ->
          val shouldSearch = request.query.isNotBlank() ||
            request.genreId != null ||
            request.datePeriod != ReleaseDatePeriod.ANY
          if (!shouldSearch) {
            resetPagination()
            _uiState.value = _uiState.value.copy(
              isLoading = false,
              isLoadingNext = false,
              hasError = false,
              results = emptyList()
            )
            return@collectLatest
          }
          searchGames(request.query, request.genreId, request.sortOption, request.datePeriod)
        }
    }
  }

  fun loadScreen(
    genreId: Int?,
    openedFromHomeSearch: Boolean
  ) {
    genreIdFlow.value = genreId
    _uiState.value = _uiState.value.copy(
      genreId = genreId,
      openedFromHomeSearch = openedFromHomeSearch
    )
  }

  fun handleAction(action: SearchUiAction) {
    when (action) {
      is SearchUiAction.SearchValueChange -> {
        _uiState.value = _uiState.value.copy(searchQuery = action.value)
        searchQueryFlow.value = action.value
      }
      is SearchUiAction.QuickTagClick -> {
        _uiState.value = _uiState.value.copy(
          searchQuery = action.tagName,
          genreId = null
        )
        genreIdFlow.value = null
        searchQueryFlow.value = action.tagName
      }
      is SearchUiAction.QuickGenreClick -> {
        _uiState.value = _uiState.value.copy(
          searchQuery = "",
          genreId = action.genreId
        )
        searchQueryFlow.value = ""
        genreIdFlow.value = action.genreId
      }
      is SearchUiAction.SortOptionClick -> {
        _uiState.value = _uiState.value.copy(sortOption = action.sortOption)
        sortOptionFlow.value = action.sortOption
      }
      is SearchUiAction.ReleaseDatePeriodClick -> {
        _uiState.value = _uiState.value.copy(releaseDatePeriod = action.period)
        releaseDatePeriodFlow.value = action.period
      }
      SearchUiAction.LoadNextPage -> loadNextPage()
      SearchUiAction.LoadNextTrending -> loadNextTrending()
    }
  }

  fun addGameToLibrary(game: GameModel) {
    viewModelScope.launch(Dispatchers.IO) {
      libraryRepository.saveGame(game)
      _uiState.value = _uiState.value.copy(
        savedGameIds = _uiState.value.savedGameIds + game.id
      )
    }
  }

  fun removeGameFromLibrary(gameId: Int) {
    viewModelScope.launch(Dispatchers.IO) {
      libraryRepository.deleteGame(gameId)
      _uiState.value = _uiState.value.copy(
        savedGameIds = _uiState.value.savedGameIds - gameId
      )
    }
  }

  private fun loadSavedGameIds() {
    viewModelScope.launch(Dispatchers.IO) {
      val savedIds = libraryRepository.getSavedGameIds()
      _uiState.value = _uiState.value.copy(savedGameIds = savedIds)
    }
  }

  private suspend fun searchGames(
    searchQuery: String,
    genreId: Int?,
    sortOption: GameSortOption,
    releaseDatePeriod: ReleaseDatePeriod
  ) {
    resetPagination()
    _uiState.value = _uiState.value.copy(
      isLoading = true,
      isLoadingNext = false,
      hasError = false
    )
    val dateRange = releaseDatePeriod.toDateRange()
    when (val result = repository.searchGames(
      searchQuery,
      genreId,
      sortOption,
      dateRange,
      currentPage
    )) {
      is Result.Success -> {
        nextPageUrl = result.data.nextUrl
        _uiState.value = _uiState.value.copy(
          isLoading = false,
          isLoadingNext = false,
          hasError = false,
          results = result.data.games
        )
      }

      is Result.Error -> {
        _uiState.value = _uiState.value.copy(
          isLoading = false,
          isLoadingNext = false,
          hasError = true,
          results = emptyList()
        )
      }

      Result.Loading -> {
        _uiState.value = _uiState.value.copy(isLoading = true)
      }
    }
  }

  private fun loadNextPage() {
    if (isPaging || nextPageUrl == null) return
    val currentState = _uiState.value
    if (currentState.searchQuery.isBlank() && currentState.genreId == null) return

    viewModelScope.launch {
      isPaging = true
      _uiState.value = _uiState.value.copy(isLoadingNext = true)
      val nextPage = currentPage + 1
      when (val result = repository.searchGames(
        currentState.searchQuery,
        currentState.genreId,
        currentState.sortOption,
        currentState.releaseDatePeriod.toDateRange(),
        nextPage
      )) {
        is Result.Success -> {
          currentPage = nextPage
          nextPageUrl = result.data.nextUrl
          _uiState.value = _uiState.value.copy(
            isLoadingNext = false,
            results = _uiState.value.results + result.data.games
          )
        }

        is Result.Error -> {
          _uiState.value = _uiState.value.copy(isLoadingNext = false)
        }

        Result.Loading -> {
          _uiState.value = _uiState.value.copy(isLoadingNext = true)
        }
      }
      isPaging = false
    }
  }

  private fun resetPagination() {
    currentPage = 1
    nextPageUrl = null
    isPaging = false
  }

  private fun loadQuickFilters() {
    viewModelScope.launch {
      filtersRepository.getPopularGenres().collectLatest { genresResult ->
        when (genresResult) {
          is Result.Success -> {
            _uiState.value = _uiState.value.copy(quickGenres = genresResult.data)
          }
          is Result.Error -> Unit
          Result.Loading -> Unit
        }
      }
      filtersRepository.getPopularTags().collectLatest { tagsResult ->
        when (tagsResult) {
          is Result.Success -> {
            val tags = tagsResult.data.filter { it.language == null || it.language == "eng" }
            _uiState.value = _uiState.value.copy(quickTags = tags)
          }
          is Result.Error -> Unit
          Result.Loading -> Unit
        }
      }
      filtersRepository.getPlatforms().collectLatest { platformsResult ->
        when (platformsResult) {
          is Result.Success -> {
            _uiState.value = _uiState.value.copy(platforms = platformsResult.data)
          }
          is Result.Error -> Unit
          Result.Loading -> Unit
        }
      }
      filtersRepository.getStores().collectLatest { storesResult ->
        when (storesResult) {
          is Result.Success -> {
            _uiState.value = _uiState.value.copy(stores = storesResult.data)
          }
          is Result.Error -> Unit
          Result.Loading -> Unit
        }
      }
      filtersRepository.getDevelopers().collectLatest { developersResult ->
        when (developersResult) {
          is Result.Success -> {
            _uiState.value = _uiState.value.copy(developers = developersResult.data)
          }
          is Result.Error -> Unit
          Result.Loading -> Unit
        }
      }
      filtersRepository.getPublishers().collectLatest { publishersResult ->
        when (publishersResult) {
          is Result.Success -> {
            _uiState.value = _uiState.value.copy(publishers = publishersResult.data)
          }
          is Result.Error -> Unit
          Result.Loading -> Unit
        }
      }
    }
  }

  private fun loadTrendingGames() {
    viewModelScope.launch {
      when (val result = gameRepository.getTopRated(page = 1)) {
        is Result.Success -> {
          trendingPage = 1
          trendingNextUrl = result.data.nextUrl
          _uiState.value = _uiState.value.copy(trendingGames = result.data.games)
        }
        is Result.Error -> Unit
        Result.Loading -> Unit
      }
    }
  }

  private fun loadNextTrending() {
    if (isPagingTrending || trendingNextUrl == null) return
    viewModelScope.launch {
      isPagingTrending = true
      _uiState.value = _uiState.value.copy(isLoadingTrendingNext = true)
      val nextPage = trendingPage + 1
      when (val result = gameRepository.getTopRated(page = nextPage)) {
        is Result.Success -> {
          trendingPage = nextPage
          trendingNextUrl = result.data.nextUrl
          _uiState.value = _uiState.value.copy(
            isLoadingTrendingNext = false,
            trendingGames = _uiState.value.trendingGames + result.data.games
          )
        }
        is Result.Error -> {
          _uiState.value = _uiState.value.copy(isLoadingTrendingNext = false)
        }
        Result.Loading -> {
          _uiState.value = _uiState.value.copy(isLoadingTrendingNext = true)
        }
      }
      isPagingTrending = false
    }
  }

  private fun ReleaseDatePeriod.toDateRange(): String? {
    val today = LocalDate.now()
    return when (this) {
      ReleaseDatePeriod.ANY -> null
      ReleaseDatePeriod.LAST_30_DAYS -> toRange(today.minusDays(30), today)
      ReleaseDatePeriod.LAST_90_DAYS -> toRange(today.minusDays(90), today)
      ReleaseDatePeriod.THIS_YEAR -> {
        val start = LocalDate.of(today.year, 1, 1)
        toRange(start, today)
      }
      ReleaseDatePeriod.LAST_YEAR -> {
        val start = LocalDate.of(today.year - 1, 1, 1)
        val end = LocalDate.of(today.year - 1, 12, 31)
        toRange(start, end)
      }
    }
  }

  private fun toRange(start: LocalDate, end: LocalDate): String {
    val formatter = DateTimeFormatter.ISO_LOCAL_DATE
    return "${start.format(formatter)},${end.format(formatter)}"
  }

  private data class SearchRequest(
    val query: String,
    val genreId: Int?,
    val sortOption: GameSortOption,
    val datePeriod: ReleaseDatePeriod
  )
}