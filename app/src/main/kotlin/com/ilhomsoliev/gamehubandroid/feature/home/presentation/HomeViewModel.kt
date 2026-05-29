package com.ilhomsoliev.gamehubandroid.feature.home.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ilhomsoliev.gamehubandroid.core.result.Result
import com.ilhomsoliev.gamehubandroid.feature.game.data.GameRepository
import com.ilhomsoliev.gamehubandroid.feature.home.presentation.HomeUiEvent.NavigateToGameDetail
import com.ilhomsoliev.gamehubandroid.feature.home.presentation.HomeUiEvent.NavigateToSearch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
  private val homeRepository: GameRepository
) : ViewModel() {

  private val _uiState = MutableStateFlow(HomeUiState())
  val uiState = _uiState.asStateFlow()

  private val _uiEvent = MutableSharedFlow<HomeUiEvent>(extraBufferCapacity = 1)
  val uiEvent = _uiEvent.asSharedFlow()

  private var newReleasesPage = 1
  private var newReleasesNextUrl: String? = null
  private var isLoadingNewReleasesNext = false

  private var topRatedPage = 1
  private var topRatedNextUrl: String? = null
  private var isLoadingTopRatedNext = false

  private var genresPage = 1
  private var genresNextUrl: String? = null
  private var isLoadingGenresNext = false

  init {
    viewModelScope.launch(Dispatchers.IO) {
      val gameDtoResult = async { homeRepository.getGameOfTheDay() }
      val newReleasesResult = async { homeRepository.getNewReleases(page = 1) }

      val genresResult = async { homeRepository.getGenres(page = 1) }
      val topRatedResult = async { homeRepository.getTopRated(page = 1) }
      val gameDto = gameDtoResult.await()
      val newReleases = newReleasesResult.await()
      val genres = genresResult.await()
      val topRated = topRatedResult.await()
      Log.d("Hello Home", """
        ${gameDto.isSuccess}
        ${newReleases.isSuccess}
        ${genres.isSuccess}
        ${topRated.isSuccess}
      """.trimIndent())
      if (gameDto is Result.Success &&
        newReleases is Result.Success &&
        genres is Result.Success &&
        topRated is Result.Success
      ) {
        newReleasesNextUrl = newReleases.data.nextUrl
        topRatedNextUrl = topRated.data.nextUrl
        genresNextUrl = genres.data.nextUrl
        _uiState.value = _uiState.value.copy(
          hasError = false,
          isLoading = false,
          gameOfTheDay = gameDto.data,
          newReleases = newReleases.data.games,
          topRated = topRated.data.games,
          genres = genres.data.genres
        )
      }
    }
  }

  fun handleAction(action: HomeUiAction) {
    when (action) {
      is HomeUiAction.OpenSearch -> {
        _uiEvent.tryEmit(NavigateToSearch(openedFromHomeSearch = true))
      }

      is HomeUiAction.OpenGameDetail -> {
        _uiEvent.tryEmit(NavigateToGameDetail(action.id))
      }

      is HomeUiAction.OpenGenreDetail -> {
        _uiEvent.tryEmit(NavigateToSearch(genreId = action.id))
      }

      HomeUiAction.LoadNextNewReleases -> loadNextNewReleases()
      HomeUiAction.LoadNextTopRated -> loadNextTopRated()
      HomeUiAction.LoadNextGenres -> loadNextGenres()
    }
  }

  private fun loadNextNewReleases() {
    if (isLoadingNewReleasesNext || newReleasesNextUrl == null) return
    viewModelScope.launch(Dispatchers.IO) {
      isLoadingNewReleasesNext = true
      _uiState.value = _uiState.value.copy(isLoadingNewReleasesNext = true)
      val nextPage = newReleasesPage + 1
      when (val result = homeRepository.getNewReleases(nextPage)) {
        is Result.Success -> {
          newReleasesPage = nextPage
          newReleasesNextUrl = result.data.nextUrl
          _uiState.value = _uiState.value.copy(
            isLoadingNewReleasesNext = false,
            newReleases = _uiState.value.newReleases.orEmpty() + result.data.games
          )
        }

        is Result.Error -> {
          _uiState.value = _uiState.value.copy(isLoadingNewReleasesNext = false)
        }

        Result.Loading -> {
          _uiState.value = _uiState.value.copy(isLoadingNewReleasesNext = true)
        }
      }
      isLoadingNewReleasesNext = false
    }
  }

  private fun loadNextTopRated() {
    if (isLoadingTopRatedNext || topRatedNextUrl == null) return
    viewModelScope.launch(Dispatchers.IO) {
      isLoadingTopRatedNext = true
      _uiState.value = _uiState.value.copy(isLoadingTopRatedNext = true)
      val nextPage = topRatedPage + 1
      when (val result = homeRepository.getTopRated(nextPage)) {
        is Result.Success -> {
          topRatedPage = nextPage
          topRatedNextUrl = result.data.nextUrl
          _uiState.value = _uiState.value.copy(
            isLoadingTopRatedNext = false,
            topRated = _uiState.value.topRated.orEmpty() + result.data.games
          )
        }

        is Result.Error -> {
          _uiState.value = _uiState.value.copy(isLoadingTopRatedNext = false)
        }

        Result.Loading -> {
          _uiState.value = _uiState.value.copy(isLoadingTopRatedNext = true)
        }
      }
      isLoadingTopRatedNext = false
    }
  }

  private fun loadNextGenres() {
    if (isLoadingGenresNext || genresNextUrl == null) return
    viewModelScope.launch(Dispatchers.IO) {
      isLoadingGenresNext = true
      _uiState.value = _uiState.value.copy(isLoadingGenresNext = true)
      val nextPage = genresPage + 1
      when (val result = homeRepository.getGenres(nextPage)) {
        is Result.Success -> {
          genresPage = nextPage
          genresNextUrl = result.data.nextUrl
          _uiState.value = _uiState.value.copy(
            isLoadingGenresNext = false,
            genres = _uiState.value.genres.orEmpty() + result.data.genres
          )
        }

        is Result.Error -> {
          _uiState.value = _uiState.value.copy(isLoadingGenresNext = false)
        }

        Result.Loading -> {
          _uiState.value = _uiState.value.copy(isLoadingGenresNext = true)
        }
      }
      isLoadingGenresNext = false
    }
  }
}