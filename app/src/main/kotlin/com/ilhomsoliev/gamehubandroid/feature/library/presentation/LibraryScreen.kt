package com.ilhomsoliev.gamehubandroid.feature.library.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ilhomsoliev.gamehubandroid.core.ui.GameCardItem
import com.ilhomsoliev.gamehubandroid.core.ui.GameCardItemPlaceholder
import com.ilhomsoliev.gamehubandroid.core.ui.SpacerV
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

  if (state.isLoading && state.games.isEmpty()) {
    LazyColumn(
      modifier = Modifier
        .fillMaxSize()
        .background(AppTheme.colors.background)
        .padding(horizontal = 12.dp)
    ) {
      item {
        SpacerV(16.dp)
        Text(
          text = "Library",
          style = AppTheme.typography.title,
          color = AppTheme.colors.text
        )
        SpacerV(12.dp)
      }
      items(5) {
        GameCardItemPlaceholder()
        SpacerV(12.dp)
      }
    }
    return
  }

  if (state.games.isEmpty()) {
    Box(
      modifier = Modifier
        .fillMaxSize()
        .background(AppTheme.colors.background),
      contentAlignment = Alignment.Center
    ) {
      Text(
        text = "No saved games yet.",
        style = AppTheme.typography.title,
        color = AppTheme.colors.text
      )
    }
    return
  }

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .background(AppTheme.colors.background)
      .padding(horizontal = 12.dp)
  ) {
    item {
      SpacerV(16.dp)
      Text(
        text = "Library",
        style = AppTheme.typography.title,
        color = AppTheme.colors.text
      )
      SpacerV(12.dp)
    }
    items(state.games) { game ->
      GameCardItem(
        modifier = Modifier.clickable { onOpenGameDetail(game.id) },
        gameModel = game,
        showAddButton = true,
        isSaved = true,
        onActionClick = { viewModel.removeGame(game.id) }
      )
      SpacerV(12.dp)
    }
  }
}
