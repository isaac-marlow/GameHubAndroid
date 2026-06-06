package com.ilhomsoliev.gamehubandroid.feature.library.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ilhomsoliev.gamehubandroid.R
import com.ilhomsoliev.gamehubandroid.core.ui.GameCardItem
import com.ilhomsoliev.gamehubandroid.core.ui.GameCardItemPlaceholder
import com.ilhomsoliev.gamehubandroid.core.ui.InformationBlock
import com.ilhomsoliev.gamehubandroid.core.ui.SpacerV
import com.ilhomsoliev.gamehubandroid.core.ui.screen_skeleton.GHTopAppBar
import com.ilhomsoliev.gamehubandroid.core.ui.theme.AppTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun LibraryScreen(
  viewModel: LibraryViewModel = koinViewModel(),
  onOpenGameDetail: (Int) -> Unit = {},
) {
  val state by viewModel.uiState.collectAsState()

  LaunchedEffect(Unit) {
    viewModel.loadLibrary()
  }

  LibraryContent(
    state,
  ) {
    when (it) {
      is LibraryUiAction.OpenGameDetail -> onOpenGameDetail(it.gameId)
      is LibraryUiAction.RemoveGame -> viewModel.removeGame(it.gameId)
      else -> {}
    }
  }
}

@Composable
fun LibraryContent(
  state: LibraryUiState,
  onAction: (LibraryUiAction) -> Unit
) {
  Scaffold(
    containerColor = AppTheme.colors.surface,
    topBar = {
      GHTopAppBar(title = "Library")
    }
  ) { paddingValues ->
    LazyColumn(
      modifier = Modifier
        .fillMaxSize()
        .padding(
          top = paddingValues.calculateTopPadding(),
          bottom = paddingValues.calculateBottomPadding(),
          start = 16.dp,
          end = 16.dp
        )
    ) {
      when (state) {
        is LibraryUiState.Content -> {
          items(state.games) { game ->
            GameCardItem(
              modifier = Modifier.clickable { onAction(LibraryUiAction.OpenGameDetail(game.id)) },
              gameModel = game,
              showAddButton = true,
              isSaved = true,
              onActionClick = { onAction(LibraryUiAction.RemoveGame(game.id)) }
            )
            SpacerV(12.dp)
          }
        }

        LibraryUiState.Empty -> {
          item {
            InformationBlock(
              modifier = Modifier.fillMaxWidth(),
              imageId = R.drawable.empty_content,
              title = "Your Library is Empty",
              subtitle = "Games you install or add to your favorites will appear here.",
              buttonIcon = Icons.Default.Explore,
              buttonText = "Explore Games",
              onButtonClick = { onAction(LibraryUiAction.OpenExplore) },
            )
          }
        }

        LibraryUiState.Error -> {
          item {
            InformationBlock(
              modifier = Modifier.fillMaxWidth(),
              imageId = R.drawable.error_icon,
              title = "Unable to Load Library",
              subtitle = "We couldn't connect to the gaming nexus servers. Please check your network connection and attempt to reconnect.",
              buttonIcon = Icons.Default.RestartAlt,
              buttonText = "Retry",
              onButtonClick = { onAction(LibraryUiAction.OpenExplore) }
            )
          }
        }

        LibraryUiState.Loading -> {
          items(5) {
            GameCardItemPlaceholder()
            SpacerV(12.dp)
          }
        }
      }
    }
  }
}
