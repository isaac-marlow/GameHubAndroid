package com.ilhomsoliev.gamehubandroid.feature.home.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ilhomsoliev.gamehubandroid.core.ui.GlideAsyncImage
import com.ilhomsoliev.gamehubandroid.core.ui.RatingComponent
import com.ilhomsoliev.gamehubandroid.core.ui.RatingComponentType
import com.ilhomsoliev.gamehubandroid.core.ui.SpacerV
import com.ilhomsoliev.gamehubandroid.core.ui.modifier.verticalGradientScrim
import com.ilhomsoliev.gamehubandroid.core.ui.theme.AppTheme
import com.ilhomsoliev.gamehubandroid.feature.game.domain.GameModel

@Composable
fun GameOfTheDayBoard(
  modifier: Modifier = Modifier,
  game: GameModel?,
  onClick: () -> Unit,
) {
  val config = LocalConfiguration.current
  val screenWidthDp = config.screenWidthDp

  Box(
    modifier = modifier
      .size(screenWidthDp.dp)
      .clip(RoundedCornerShape(12.dp))
      .clickable {
        onClick()
      }
  ) {
    if (game == null) {
      return
    }
    GlideAsyncImage(
      imageUrl = game.backgroundImage,
      contentDescription = game.name,
      modifier = Modifier
        .fillMaxSize()
        .verticalGradientScrim(),
      contentScale = ContentScale.Crop
    )
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(24.dp),
      verticalArrangement = Arrangement.Bottom,
      horizontalAlignment = Alignment.Start
    ) {
      Text(
        text = "GAME OF THE DAY",
        style = AppTheme.typography.label,
        color = AppTheme.colors.primary
      )
      SpacerV(12.dp)
      Text(
        text = game.name,
        style = AppTheme.typography.title.copy(fontSize = 32.sp),
        color = AppTheme.colors.text
      )
      SpacerV(12.dp)
      Text(
        text = "Released: ${game.releasedDate}",
        style = AppTheme.typography.body,
        color = AppTheme.colors.secondaryText
      )
      SpacerV(12.dp)
      Row(verticalAlignment = Alignment.CenterVertically) {
        RatingComponent(
          modifier = Modifier.padding(end = 16.dp),
          type = RatingComponentType.Big,
          value = game.rating
        )
        DetailsButton(onClick = {
          onClick()
        })
      }
    }
  }
}

@Composable
private fun DetailsButton(
  modifier: Modifier = Modifier,
  onClick: () -> Unit
) {
  Row(
    modifier = modifier
      .clip(RoundedCornerShape(AppTheme.shapes.radiusXl))
      .background(AppTheme.colors.primary)
      .clickable {
        onClick()
      },
    horizontalArrangement = Arrangement.Center,
    verticalAlignment = Alignment.CenterVertically,
  ) {
    Icon(
      modifier = Modifier
        .padding(start = 6.dp),
      imageVector = Icons.Default.PlayArrow,
      contentDescription = "",
      tint = AppTheme.colors.primaryText
    )
    Text(
      modifier = Modifier
        .padding(top = 8.dp, end = 8.dp, bottom = 8.dp),
      text = "View details",
      style = AppTheme.typography.label,
      color = AppTheme.colors.primaryText
    )
  }
}