package com.ilhomsoliev.gamehubandroid.feature.search.presentation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ilhomsoliev.gamehubandroid.core.ui.GHChip
import com.ilhomsoliev.gamehubandroid.core.ui.GHTextField
import com.ilhomsoliev.gamehubandroid.core.ui.GameCardItem
import com.ilhomsoliev.gamehubandroid.core.ui.GameCardItemPlaceholder
import com.ilhomsoliev.gamehubandroid.core.ui.SpacerH
import com.ilhomsoliev.gamehubandroid.core.ui.SpacerV
import com.ilhomsoliev.gamehubandroid.core.ui.modifier.gameHubRoundedBackground
import com.ilhomsoliev.gamehubandroid.core.ui.theme.AppTheme
import com.ilhomsoliev.gamehubandroid.feature.game.domain.GameModel
import com.ilhomsoliev.gamehubandroid.feature.search.data.GameSortOption
import kotlinx.coroutines.flow.distinctUntilChanged
import org.koin.androidx.compose.koinViewModel

@Composable
fun SearchScreen(
  genreId: Int? = null,
  openedFromHomeSearch: Boolean = false,
  viewModel: SearchViewModel = koinViewModel(),
  onOpenGameDetail: (Int) -> Unit = {},
  onBack: () -> Unit = {},
) {

  val state by viewModel.uiState.collectAsState()

  LaunchedEffect(Unit) {
    viewModel.loadScreen(genreId, openedFromHomeSearch)
  }

  SearchContent(
    state = state,
    handleAction = { viewModel.handleAction(it) },
    onAddToLibrary = { viewModel.addGameToLibrary(it) },
    onRemoveFromLibrary = { viewModel.removeGameFromLibrary(it) },
    onOpenGameDetail = onOpenGameDetail,
    onBack = onBack,
    openedFromHomeSearch = openedFromHomeSearch
  )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchContent(
  state: SearchUiState,
  handleAction: (SearchUiAction) -> Unit,
  onAddToLibrary: (GameModel) -> Unit,
  onRemoveFromLibrary: (Int) -> Unit,
  onOpenGameDetail: (Int) -> Unit,
  onBack: () -> Unit,
  openedFromHomeSearch: Boolean,
) {
  val listState = rememberLazyListState()
  val isLoading by rememberUpdatedState(state.isLoading)
  val isLoadingNext by rememberUpdatedState(state.isLoadingNext)
  val isLoadingTrendingNext by rememberUpdatedState(state.isLoadingTrendingNext)
  val resultsCount by rememberUpdatedState(state.results.size)
  val trendingCount by rememberUpdatedState(state.trendingGames.size)
  val currentQuery by rememberUpdatedState(state.searchQuery)
  val isDefaultState by rememberUpdatedState(state.results.isEmpty() && state.searchQuery.isEmpty())
  val showFilters = rememberSaveable { mutableStateOf(false) }
  val focusRequester = remember { FocusRequester() }
  val keyboardController = LocalSoftwareKeyboardController.current

  BackHandler {
    if (currentQuery.isBlank()) {
      onBack()
    } else {
      handleAction(SearchUiAction.SearchValueChange(""))
    }
  }

  LaunchedEffect(openedFromHomeSearch) {
    if (openedFromHomeSearch) {
      focusRequester.requestFocus()
      keyboardController?.show()
    }
  }

  LaunchedEffect(listState) {
    snapshotFlow {
      val lastVisible = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
      lastVisible to listState.layoutInfo.totalItemsCount
    }.distinctUntilChanged().collect { (lastVisible, totalCount) ->
      val shouldLoadNext = totalCount > 0 && lastVisible >= totalCount - 3
      if (shouldLoadNext && !isLoading && !isLoadingNext && resultsCount > 0 && currentQuery.isNotBlank()) {
        handleAction(SearchUiAction.LoadNextPage)
      } else if (
        shouldLoadNext &&
        isDefaultState &&
        !isLoadingTrendingNext &&
        trendingCount > 0
      ) {
        handleAction(SearchUiAction.LoadNextTrending)
      }
    }
  }

  if (showFilters.value) {
    ModalBottomSheet(
      onDismissRequest = { showFilters.value = false },
      contentColor = AppTheme.colors.surface,
      containerColor = AppTheme.colors.surface,
    ) {
      SearchFilterSheetContent(
        state = state,
        onTagClick = {
          handleAction(SearchUiAction.QuickTagClick(it))
          showFilters.value = false
        },
        onGenreClick = {
          handleAction(SearchUiAction.QuickGenreClick(it))
          showFilters.value = false
        },
        onSortOptionClick = { handleAction(SearchUiAction.SortOptionClick(it)) },
        onReleaseDatePeriodClick = { handleAction(SearchUiAction.ReleaseDatePeriodClick(it)) }
      )
    }
  }

  LazyColumn(
    state = listState,
    modifier = Modifier
      .fillMaxSize()
      .background(AppTheme.colors.surface)
  ) {
    item {
      SpacerV(16.dp)
      Text(
        modifier = Modifier.padding(horizontal = 12.dp),
        text = "Explore Games",
        style = AppTheme.typography.titleLarge.copy(fontSize = 32.sp),
        color = AppTheme.colors.onSurface
      )
    }

    item {
      SpacerV(12.dp)

      GHTextField(
        modifier = Modifier.padding(horizontal = 12.dp),
        textFieldModifier = Modifier.focusRequester(focusRequester),
        value = state.searchQuery,
        onValueChange = { handleAction(SearchUiAction.SearchValueChange(it)) },
        icon = Icons.Default.Search,
        placeholder = "Search by title, genre, developer..."
      )
    }

    item {
      SpacerV(12.dp)

      Row(
        modifier = Modifier
          .padding(horizontal = 12.dp)
          .gameHubRoundedBackground()
          .clickable { showFilters.value = true }
          .padding(horizontal = 10.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Icon(
          imageVector = Icons.Default.FilterList,
          contentDescription = "Filters",
          tint = AppTheme.colors.primary
        )
        SpacerH(6.dp)
        Text(
          text = "Filters",
          style = AppTheme.typography.bodyMedium,
          color = AppTheme.colors.primary
        )
      }
    }

    if (state.results.isEmpty() && state.searchQuery.isEmpty()) {
      item {
        SpacerV(12.dp)

        Row(
          modifier = Modifier
            .padding(horizontal = 12.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = "Quick Filters",
            style = AppTheme.typography.labelMedium,
            color = AppTheme.colors.onSurface
          )
        }
        FlowRow(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
        ) {
          state.quickTags.forEach { tag ->
            GHChip(
              modifier = Modifier.padding(end = 6.dp, top = 6.dp),
              text = tag.name,
              isActive = state.searchQuery == tag.name,
              onClick = { handleAction(SearchUiAction.QuickTagClick(tag.name)) }
            )
          }
        }
      }
      if (state.quickGenres.isNotEmpty()) {
        item {
          SpacerV(12.dp)
          Row(
            modifier = Modifier
              .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = "Genres",
              style = AppTheme.typography.labelMedium,
              color = AppTheme.colors.onSurface
            )
          }
          FlowRow(
            modifier = Modifier
              .fillMaxWidth()
              .padding(horizontal = 12.dp)
          ) {
            state.quickGenres.forEach { genre ->
              GHChip(
                modifier = Modifier.padding(end = 6.dp, top = 6.dp),
                text = genre.name,
                isActive = state.genreId == genre.id,
                onClick = { handleAction(SearchUiAction.QuickGenreClick(genre.id)) }
              )
            }
          }
        }
      }
      if (state.trendingGames.isNotEmpty()) {
        item {
          SpacerV(16.dp)
          Text(
            modifier = Modifier
              .padding(horizontal = 12.dp),
            text = "Trending Games",
            style = AppTheme.typography.labelMedium,
            color = AppTheme.colors.onSurface
          )
          SpacerV(16.dp)
        }
        items(state.trendingGames) { game ->
          val isSaved = state.savedGameIds.contains(game.id)
          GameCardItem(
            modifier = Modifier
              .padding(horizontal = 12.dp)
              .clickable { onOpenGameDetail(game.id) },
            gameModel = game,
            isSaved = isSaved,
            onActionClick = {
              if (isSaved) onRemoveFromLibrary(game.id) else onAddToLibrary(game)
            }
          )
          SpacerV(12.dp)
        }
        if (state.isLoadingTrendingNext) {
          item {
            SpacerV(12.dp)
            Text(
              modifier = Modifier.padding(horizontal = 8.dp),
              text = "Loading more...",
              style = AppTheme.typography.bodyMedium,
              color = AppTheme.colors.onSurface
            )
          }
        }
      }
    }

    if (state.isLoading && state.results.isEmpty()) {
      items(5) {
        SpacerV(12.dp)
        GameCardItemPlaceholder(
          modifier = Modifier.padding(horizontal = 12.dp)
        )
      }
    } else if (state.hasError) {
      item {
        SpacerV(16.dp)
        Text(
          modifier = Modifier.padding(horizontal = 8.dp),
          text = "Something went wrong. Please try again.",
          style = AppTheme.typography.bodyMedium,
          color = AppTheme.colors.onSurface
        )
      }
    } else if (state.results.isEmpty() && state.searchQuery.isNotBlank()) {
      item {
        SpacerV(16.dp)
        Text(
          modifier = Modifier.padding(horizontal = 8.dp),
          text = "No results found.",
          style = AppTheme.typography.bodyMedium,
          color = AppTheme.colors.onSurface
        )
      }
    } else if (state.results.isNotEmpty()) {
      item {
        SpacerV(16.dp)
      }
      items(state.results) { game ->
        val isSaved = state.savedGameIds.contains(game.id)
        GameCardItem(
          modifier = Modifier.clickable { onOpenGameDetail(game.id) },
          gameModel = game,
          isSaved = isSaved,
          onActionClick = {
            if (isSaved) onRemoveFromLibrary(game.id) else onAddToLibrary(game)
          }
        )
        SpacerV(12.dp)
      }
    }

    if (state.isLoadingNext) {
      item {
        SpacerV(12.dp)
        Text(
          modifier = Modifier.padding(horizontal = 8.dp),
          text = "Loading more...",
          style = AppTheme.typography.bodyMedium,
          color = AppTheme.colors.onSurface
        )
      }
    }
  }
}

@Composable
private fun SearchFilterSheetContent(
  state: SearchUiState,
  onTagClick: (String) -> Unit,
  onGenreClick: (Int) -> Unit,
  onSortOptionClick: (GameSortOption) -> Unit,
  onReleaseDatePeriodClick: (ReleaseDatePeriod) -> Unit,
) {
  LazyColumn(
    modifier = Modifier
      .fillMaxWidth()
      .background(AppTheme.colors.surface)
  ) {
    item {
      Row(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
        Text(
          text = "Filters",
          style = AppTheme.typography.labelMedium,
          color = AppTheme.colors.onSurface
        )
      }

      Text(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp),
        text = "Sort by",
        style = AppTheme.typography.labelMedium,
        color = AppTheme.colors.onSurface
      )
      FlowRow(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp)
      ) {
        GameSortOption.values().forEach { option ->
          GHChip(
            modifier = Modifier.padding(end = 6.dp, top = 6.dp),
            text = option.displayName(),
            isActive = state.sortOption == option,
            onClick = { onSortOptionClick(option) }
          )
        }
      }

      SpacerV(12.dp)

      Text(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp),
        text = "Release date",
        style = AppTheme.typography.labelMedium,
        color = AppTheme.colors.onSurface
      )
      FlowRow(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp)
      ) {
        ReleaseDatePeriod.values().forEach { period ->
          GHChip(
            modifier = Modifier.padding(end = 6.dp, top = 6.dp),
            text = period.label,
            isActive = state.releaseDatePeriod == period,
            onClick = { onReleaseDatePeriodClick(period) }
          )
        }
      }

      SpacerV(12.dp)

      if (state.quickTags.isNotEmpty()) {
        Text(
          modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp),
          text = "Tags",
          style = AppTheme.typography.labelMedium,
          color = AppTheme.colors.onSurface
        )
        FlowRow(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
        ) {
          state.quickTags.forEach { tag ->
            GHChip(
              modifier = Modifier.padding(end = 6.dp, top = 6.dp),
              text = tag.name,
              isActive = state.searchQuery == tag.name,
              onClick = { onTagClick(tag.name) }
            )
          }
        }
      }

      if (state.quickGenres.isNotEmpty()) {
        SpacerV(12.dp)
        Text(
          modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp),
          text = "Genres",
          style = AppTheme.typography.labelMedium,
          color = AppTheme.colors.onSurface
        )
        FlowRow(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
        ) {
          state.quickGenres.forEach { genre ->
            GHChip(
              modifier = Modifier.padding(end = 6.dp, top = 6.dp),
              text = genre.name,
              isActive = state.genreId == genre.id,
              onClick = { onGenreClick(genre.id) }
            )
          }
        }
      }

      if (state.platforms.isNotEmpty()) {
        SpacerV(12.dp)
        Text(
          modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp),
          text = "Platforms",
          style = AppTheme.typography.labelMedium,
          color = AppTheme.colors.onSurface
        )
        FlowRow(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
        ) {
          state.platforms.forEach { platform ->
            GHChip(
              modifier = Modifier.padding(end = 6.dp, top = 6.dp),
              text = platform.name,
              isActive = state.searchQuery == platform.name,
              onClick = { onTagClick(platform.name) }
            )
          }
        }
      }

      if (state.stores.isNotEmpty()) {
        SpacerV(12.dp)
        Text(
          modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp),
          text = "Stores",
          style = AppTheme.typography.labelMedium,
          color = AppTheme.colors.onSurface
        )
        FlowRow(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
        ) {
          state.stores.forEach { store ->
            GHChip(
              modifier = Modifier.padding(end = 6.dp, top = 6.dp),
              text = store.name,
              isActive = state.searchQuery == store.name,
              onClick = { onTagClick(store.name) }
            )
          }
        }
      }

      if (state.developers.isNotEmpty()) {
        SpacerV(12.dp)
        Text(
          modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp),
          text = "Developers",
          style = AppTheme.typography.labelMedium,
          color = AppTheme.colors.onSurface
        )
        FlowRow(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
        ) {
          state.developers.forEach { developer ->
            GHChip(
              modifier = Modifier.padding(end = 6.dp, top = 6.dp),
              text = developer.name,
              isActive = state.searchQuery == developer.name,
              onClick = { onTagClick(developer.name) }
            )
          }
        }
      }

      if (state.publishers.isNotEmpty()) {
        SpacerV(12.dp)
        Text(
          modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp),
          text = "Publishers",
          style = AppTheme.typography.labelMedium,
          color = AppTheme.colors.onSurface
        )
        FlowRow(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
        ) {
          state.publishers.forEach { publisher ->
            GHChip(
              modifier = Modifier.padding(end = 6.dp, top = 6.dp),
              text = publisher.name,
              isActive = state.searchQuery == publisher.name,
              onClick = { onTagClick(publisher.name) }
            )
          }
        }
      }

      SpacerV(24.dp)
    }
  }
}

private fun GameSortOption.displayName(): String {
  return when (this) {
    GameSortOption.RELEVANCE -> "Relevance"
    GameSortOption.NAME -> "Name (A-Z)"
    GameSortOption.NAME_DESC -> "Name (Z-A)"
    GameSortOption.RELEASED -> "Released (Newest)"
    GameSortOption.RELEASED_OLD -> "Released (Oldest)"
    GameSortOption.ADDED -> "Most Added"
    GameSortOption.CREATED -> "Recently Added"
    GameSortOption.UPDATED -> "Recently Updated"
    GameSortOption.RATING -> "Top Rated"
    GameSortOption.METACRITIC -> "Top Metacritic"
  }
}
