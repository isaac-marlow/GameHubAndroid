package com.ilhomsoliev.gamehubandroid.feature.details.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ilhomsoliev.gamehubandroid.core.ui.SpacerV
import com.ilhomsoliev.gamehubandroid.core.ui.theme.AppTheme
import com.ilhomsoliev.gamehubandroid.feature.details.presentation.components.AboutGame
import com.ilhomsoliev.gamehubandroid.feature.details.presentation.components.CommunityDiscussion
import com.ilhomsoliev.gamehubandroid.feature.details.presentation.components.GameDetailsTitle
import com.ilhomsoliev.gamehubandroid.feature.details.presentation.components.GameInfo
import com.ilhomsoliev.gamehubandroid.feature.details.presentation.components.GameScreenshot
import com.ilhomsoliev.gamehubandroid.feature.details.presentation.components.GameSystemRequirements
import com.ilhomsoliev.gamehubandroid.feature.details.presentation.components.ScreenshotViewerDialog
import com.ilhomsoliev.gamehubandroid.feature.details.presentation.components.WhereToBuy
import com.ilhomsoliev.gamehubandroid.feature.home.presentation.components.GameCarouselItem
import org.koin.androidx.compose.koinViewModel

@Composable
fun GameDetailScreen(
  gameId: Int,
  viewModel: GameDetailViewModel = koinViewModel(),
  onBack: () -> Unit,
  onOpenGameDetail: (Int) -> Unit = {},
) {

  val state by viewModel.uiState.collectAsState()

  LaunchedEffect(Unit) {
    viewModel.getGameById(gameId)
  }

  GameDetailContent(state, onOpenGameDetail) {
    viewModel.handleAction(it)
  }
}

@Composable
fun GameDetailContent(
  state: GameDetailUiState,
  onOpenGameDetail: (Int) -> Unit,
  handleAction: (GameDetailUiAction) -> Unit,
) {
  var selectedScreenshotIndex by remember { mutableStateOf<Int?>(null) }

  when (state) {
    GameDetailUiState.Error -> {

    }

    is GameDetailUiState.Loaded -> {


      LazyColumn(
        modifier = Modifier
          .fillMaxWidth()
      ) {
        item {
          GameDetailsTitle(
            image = state.game.backgroundImage,
            title = state.game.name,
            releaseDate = state.game.releasedDate,
            isAvailableOnPc = state.game.isAvailableOnPc(),
            isAvailableOnConsole = state.game.isAvailableOnConsole(),
            onBack = { handleAction(GameDetailUiAction.OnBack) },
            onShare = { handleAction(GameDetailUiAction.Share) }
          )
        }

        item {
          SpacerV(16.dp)

          WhereToBuy(
            stores = state.game.stores
          )
        }

        if (state.game.description?.isNotEmpty() == true) {
          item {
            SpacerV(16.dp)

            AboutGame(
              description = state.game.description
            )
          }
        }

        item {
          SpacerV(16.dp)

          GameInfo(
            genres = state.game.genres,
            tags = state.game.tags,
            esrbRating = state.game.esrbRating?.name
          )
        }

        item {
          SpacerV(16.dp)

          GameScreenshot(
            screenshots = state.screenshots,
            onScreenshotClick = { index -> selectedScreenshotIndex = index }
          )
        }

        item {
          SpacerV(16.dp)

          GameSystemRequirements(requirements = state.game.platforms.firstOrNull { it.requirements != null }?.requirements)
        }
        item {
          SpacerV(16.dp)

          CommunityDiscussion(
            posts = state.redditPosts
          )
        }
        if (state.similarGames.isNotEmpty()) {
          item {
            SpacerV(16.dp)
            Text(
              modifier = Modifier.padding(horizontal = 12.dp),
              text = "Visually similar games",
              style = AppTheme.typography.title.copy(fontSize = 18.sp),
              color = AppTheme.colors.text
            )
            SpacerV(12.dp)
            BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
              val horizontalPadding = 8.dp
              val gridSpacing = 12.dp
              val cellWidth = (maxWidth - gridSpacing) / 2
              val itemWidth = (cellWidth - horizontalPadding * 2).coerceAtLeast(0.dp)
              val itemHeight = itemWidth * 1.22f
              state.similarGames.chunked(2).forEach { rowGames ->
                Row(
                  modifier = Modifier.fillMaxWidth(),
                  horizontalArrangement = Arrangement.spacedBy(gridSpacing)
                ) {
                  rowGames.forEach { game ->
                    GameCarouselItem(
                      modifier = Modifier.weight(1f),
                      game = game,
                      itemWidth = itemWidth,
                      itemHeight = itemHeight,
                      horizontalPadding = horizontalPadding,
                      onClick = { onOpenGameDetail(game.id) }
                    )
                  }
                }
                SpacerV(12.dp)
              }
            }
          }
        }
      }

      val index = selectedScreenshotIndex
      if (index != null && state.screenshots.isNotEmpty()) {
        ScreenshotViewerDialog(
          screenshots = state.screenshots,
          initialIndex = index,
          onDismiss = { selectedScreenshotIndex = null }
        )
      }
    }

    GameDetailUiState.Loading -> {
      CircularProgressIndicator()
    }
  }
}
