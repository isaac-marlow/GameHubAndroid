package com.ilhomsoliev.gamehubandroid.feature.home.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ilhomsoliev.gamehubandroid.core.ui.GlideAsyncImage
import com.ilhomsoliev.gamehubandroid.core.ui.SpacerV
import com.ilhomsoliev.gamehubandroid.core.ui.modifier.verticalGradientScrim
import com.ilhomsoliev.gamehubandroid.core.ui.theme.AppTheme
import com.ilhomsoliev.gamehubandroid.feature.game.domain.GenreModel
import kotlinx.coroutines.flow.distinctUntilChanged

@Composable
fun LazyItemScope.GenreCarouselWithLabel(
  modifier: Modifier = Modifier,
  genres: List<GenreModel>,
  onGenreClick: (id: Int) -> Unit,
  onLoadMore: () -> Unit = {}
) {

  val config = LocalConfiguration.current
  val rows = 2
  val spacing = 8.dp
  val gridPadding = 8.dp
  val gridState = rememberLazyGridState()
  val genresCount by rememberUpdatedState(genres.size)

  LaunchedEffect(gridState) {
    snapshotFlow {
      val lastVisible = gridState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
      lastVisible to gridState.layoutInfo.totalItemsCount
    }.distinctUntilChanged().collect { (lastVisible, totalCount) ->
      val shouldLoadNext = totalCount > 0 && lastVisible >= totalCount - 3
      if (shouldLoadNext && genresCount > 0) {
        onLoadMore()
      }
    }
  }
  val availableWidth = config.screenWidthDp.dp - (gridPadding * 2f) - spacing
  val itemWidth = availableWidth / 1.5f
  val itemHeight = itemWidth
  val gridHeight =
    (itemHeight * rows) + (spacing * (rows - 1)) + (gridPadding * 2)

  Text(
    text = "Browse by Genre",
    modifier = Modifier.padding(horizontal = 12.dp),
    style = AppTheme.typography.labelMedium.copy(fontSize = 18.sp),
    color = AppTheme.colors.onSurface
  )
  SpacerV(16.dp)

  LazyHorizontalGrid(
    rows = GridCells.Fixed(rows),
    verticalArrangement = Arrangement.spacedBy(spacing),
    horizontalArrangement = Arrangement.spacedBy(spacing),
    contentPadding = PaddingValues(gridPadding),
    modifier = Modifier
      .fillMaxWidth()
      .height(gridHeight),
    state = gridState
  ) {
    this@LazyHorizontalGrid.items(
      items = genres,
      key = { it.id }
    ) { genreModel ->
      Box(contentAlignment = Alignment.Center) {
        GlideAsyncImage(
          modifier = Modifier
            .height(itemHeight)
            .width(itemWidth)
            .clip(RoundedCornerShape(AppTheme.shapes.cardRadius))
            .verticalGradientScrim()
            .clickable {
              onGenreClick(genreModel.id)
            },
          imageUrl = genreModel.imageBackground,
          contentDescription = "genre",
          contentScale = ContentScale.Crop
        )
        Text(
          text = genreModel.name,
          color = AppTheme.colors.onSurface,
          style = AppTheme.typography.labelMedium
        )
      }
    }
  }
}