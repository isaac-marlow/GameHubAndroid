package com.ilhomsoliev.gamehubandroid.feature.home.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.VideogameAsset
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ilhomsoliev.gamehubandroid.core.ui.GlideAsyncImage
import com.ilhomsoliev.gamehubandroid.core.ui.RatingComponent
import com.ilhomsoliev.gamehubandroid.core.ui.RatingComponentType
import com.ilhomsoliev.gamehubandroid.core.ui.SpacerH
import com.ilhomsoliev.gamehubandroid.core.ui.SpacerV
import com.ilhomsoliev.gamehubandroid.core.ui.modifier.verticalGradientScrim
import com.ilhomsoliev.gamehubandroid.core.ui.theme.AppTheme
import com.ilhomsoliev.gamehubandroid.feature.game.domain.GameModel

@Composable
fun GameCarouselItem(
  modifier: Modifier = Modifier,
  game: GameModel,
  itemWidth: Dp = 180.dp,
  itemHeight: Dp = 220.dp,
  horizontalPadding: Dp = 16.dp,
  onClick: () -> Unit
) {
  Box(
    modifier = modifier
      .fillMaxWidth()
      .size(itemWidth, itemHeight)
      .padding(horizontal = horizontalPadding)
      .clip(RoundedCornerShape(AppTheme.shapes.cardRadius))
      .clickable {
        onClick()
      }
  ) {
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
        .padding(12.dp),
      horizontalAlignment = Alignment.Start,
      verticalArrangement = Arrangement.Bottom
    ) {
      Row(verticalAlignment = Alignment.CenterVertically) {
        if (game.isAvailableOnPc()) {
          Icon(
            modifier = Modifier.size(16.dp),
            imageVector = Icons.Default.Computer,
            contentDescription = "computer",
            tint = AppTheme.colors.onSurface
          )
        }
        if (game.isAvailableOnConsole()) {
          SpacerH(4.dp)

          Icon(
            modifier = Modifier.size(16.dp),
            imageVector = Icons.Default.VideogameAsset,
            contentDescription = "console",
            tint = AppTheme.colors.onSurface
          )
        }

        if (game.hasAnyPlatform()) {
          SpacerH(6.dp)
        }

        RatingComponent(type = RatingComponentType.Small, value = game.rating)
      }
      SpacerV(12.dp)
      Text(
        text = game.name,
        color = AppTheme.colors.onSurface,
        style = AppTheme.typography.titleLarge.copy(fontSize = 16.sp),
      )
    }
  }
}