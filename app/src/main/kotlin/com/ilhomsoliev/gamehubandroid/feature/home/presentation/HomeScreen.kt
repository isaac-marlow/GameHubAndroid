package com.ilhomsoliev.gamehubandroid.feature.home.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ilhomsoliev.gamehubandroid.core.ui.GHTextField
import com.ilhomsoliev.gamehubandroid.core.ui.SpacerV
import com.ilhomsoliev.gamehubandroid.core.ui.modifier.shimmer
import com.ilhomsoliev.gamehubandroid.core.ui.theme.AppTheme
import com.ilhomsoliev.gamehubandroid.feature.home.presentation.components.GameOfTheDayBoard
import com.ilhomsoliev.gamehubandroid.feature.home.presentation.components.GamesCarouselWithLabel
import com.ilhomsoliev.gamehubandroid.feature.home.presentation.components.GenreCarouselWithLabel
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
  viewModel: HomeViewModel = koinViewModel(),
  onOpenGameDetail: (Int) -> Unit = {},
  onOpenSearch: (Int?, Boolean) -> Unit = { _, _ -> },
) {

  val state by viewModel.uiState.collectAsState()

  LaunchedEffect(viewModel) {
    viewModel.uiEvent.collect { event ->
      when (event) {
        is HomeUiEvent.NavigateToGameDetail -> onOpenGameDetail(event.id)
        is HomeUiEvent.NavigateToSearch ->
          onOpenSearch(event.genreId, event.openedFromHomeSearch)
      }
    }
  }

  HomeContent(state) {
    viewModel.handleAction(it)
  }
}

@Composable
private fun HomeContent(
  state: HomeUiState,
  handleAction: (HomeUiAction) -> Unit,
) {
  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .background(AppTheme.colors.surface)
  ) {

    item {
      SpacerV(8.dp)
      Text(
        modifier = Modifier.padding(horizontal = 8.dp),
        text = "Welcome back,",
        style = AppTheme.typography.bodyMedium,
        color = AppTheme.colors.onSurface
      )
      SpacerV(8.dp)
      Text(
        modifier = Modifier.padding(horizontal = 8.dp),
        text = "Gamer",
        style = AppTheme.typography.titleLarge.copy(fontSize = 32.sp),
        color = AppTheme.colors.onSurface
      )
    }

    item {
      SpacerV(12.dp)

      GHTextField(
        modifier = Modifier
          .padding(horizontal = 8.dp)
          .shimmer(state.isLoading),
        value = state.searchQuery,
        onValueChange = { },
        onClick = { handleAction(HomeUiAction.OpenSearch) },
        icon = Icons.Default.Search,
        placeholder = "Search games..."
      )
    }

    item {
      SpacerV(12.dp)

      GameOfTheDayBoard(
        modifier = Modifier
          .padding(horizontal = 8.dp)
          .shimmer(state.isLoading),
        game = state.gameOfTheDay,
        onClick = {
          state.gameOfTheDay?.id?.let {
            handleAction(HomeUiAction.OpenGameDetail(it))
          }
        })
    }

    item {
      SpacerV(24.dp)
      state.newReleases?.let {
        GamesCarouselWithLabel(
          games = state.newReleases,
          label = "New releases",
          onClick = { gameId ->
            handleAction(HomeUiAction.OpenGameDetail(gameId))
          },
          onLoadMore = { handleAction(HomeUiAction.LoadNextNewReleases) }
        )
      }
    }

    if (!state.topRated.isNullOrEmpty()) {
      item {
        SpacerV(24.dp)

        GamesCarouselWithLabel(
          games = state.topRated,
          label = "Top rated",
          onClick = { gameId ->
            handleAction(HomeUiAction.OpenGameDetail(gameId))
          },
          onLoadMore = { handleAction(HomeUiAction.LoadNextTopRated) }
        )
      }
    }

    if (!state.genres.isNullOrEmpty()) {
      item {
        SpacerV(24.dp)
        GenreCarouselWithLabel(
          genres = state.genres,
          onGenreClick = {
            handleAction(HomeUiAction.OpenGenreDetail(it))
          },
          onLoadMore = { handleAction(HomeUiAction.LoadNextGenres) }
        )
      }
    }
  }
}
