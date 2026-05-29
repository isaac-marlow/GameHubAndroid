package com.ilhomsoliev.gamehubandroid.feature.details.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ilhomsoliev.gamehubandroid.core.ui.GlideAsyncImage
import com.ilhomsoliev.gamehubandroid.core.ui.SpacerV
import com.ilhomsoliev.gamehubandroid.core.ui.theme.AppTheme
import com.ilhomsoliev.gamehubandroid.feature.game.domain.ScreenshotModel

@Composable
fun LazyItemScope.GameScreenshot(
  modifier: Modifier = Modifier,
  screenshots: List<ScreenshotModel>,
  onScreenshotClick: (Int) -> Unit = {}
) {

  Column(
    modifier = modifier
      .fillMaxWidth()
      .padding(horizontal = 12.dp),
    horizontalAlignment = Alignment.Start,
    verticalArrangement = Arrangement.Center
  ) {
    Text(
      text = "Screenshots & Media",
      style = AppTheme.typography.title.copy(fontSize = 18.sp),
      color = AppTheme.colors.text
    )
    SpacerV(12.dp)

    if (screenshots.isNotEmpty()) {
      val columns = 2
      val itemHeight = 100.dp
      val spacing = 8.dp
      val gridPadding = 8.dp
      val rows = (screenshots.size + columns - 1) / columns
      val gridHeight =
        (itemHeight * rows) + (spacing * (rows - 1)) + (gridPadding * 2)

      LazyVerticalGrid(
        columns = GridCells.Fixed(columns),
        verticalArrangement = Arrangement.spacedBy(spacing),
        horizontalArrangement = Arrangement.spacedBy(spacing),
        contentPadding = PaddingValues(gridPadding),
        modifier = Modifier
          .fillMaxWidth()
          .height(gridHeight)
      ) {
        this@LazyVerticalGrid.itemsIndexed(
          items = screenshots,
          key = { _, screenshot -> screenshot.id }
        ) { index, screenshot ->
          GlideAsyncImage(
            modifier = Modifier
              .fillMaxWidth()
              .height(itemHeight)
              .clip(RoundedCornerShape(AppTheme.shapes.cardRadius))
              .clickable { onScreenshotClick(index) },
            imageUrl = screenshot.image,
            contentDescription = "screenshot",
            contentScale = ContentScale.Crop
          )
        }
      }
    }
  }
}