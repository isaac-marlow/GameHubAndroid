package com.ilhomsoliev.gamehubandroid.feature.home.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ilhomsoliev.gamehubandroid.core.ui.SpacerV
import com.ilhomsoliev.gamehubandroid.core.ui.theme.AppTheme
import com.ilhomsoliev.gamehubandroid.feature.game.domain.GameModel
import kotlinx.coroutines.flow.distinctUntilChanged

@Composable
fun GamesCarouselWithLabel(
  modifier: Modifier = Modifier,
  games: List<GameModel>,
  label: String,
  onClick: (id: Int) -> Unit,
  onLoadMore: () -> Unit = {}
) {
  val listState = rememberLazyListState()
  val gamesCount by rememberUpdatedState(games.size)

  LaunchedEffect(listState) {
    snapshotFlow {
      val lastVisible = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
      lastVisible to listState.layoutInfo.totalItemsCount
    }.distinctUntilChanged().collect { (lastVisible, totalCount) ->
      val shouldLoadNext = totalCount > 0 && lastVisible >= totalCount - 3
      if (shouldLoadNext && gamesCount > 0) {
        onLoadMore()
      }
    }
  }

  Column(modifier = modifier.fillMaxWidth()) {
    Text(
      text = label,
      modifier = Modifier.padding(horizontal = 12.dp),
      style = AppTheme.typography.labelMedium.copy(fontSize = 18.sp),
      color = AppTheme.colors.onSurface
    )
    SpacerV(16.dp)
    LazyRow(
      modifier = modifier.fillMaxWidth(),
      state = listState
    ) {
      items(games) {
        GameCarouselItem(
          game = it,
          onClick = {
            onClick(it.id)
          })
      }
    }
  }
}