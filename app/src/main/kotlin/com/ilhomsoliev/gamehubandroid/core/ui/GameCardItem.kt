package com.ilhomsoliev.gamehubandroid.core.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ilhomsoliev.gamehubandroid.core.ui.modifier.gameHubRoundedBackground
import com.ilhomsoliev.gamehubandroid.core.ui.modifier.shimmer
import com.ilhomsoliev.gamehubandroid.core.ui.theme.AppTheme
import com.ilhomsoliev.gamehubandroid.feature.game.domain.GameModel

@Composable
fun GameCardItem(
  modifier: Modifier = Modifier,
  gameModel: GameModel,
  showAddButton: Boolean = true,
  isSaved: Boolean = false,
  onActionClick: (() -> Unit)? = null,
) {
  Row(
    modifier = modifier
      .fillMaxWidth()
      .gameHubRoundedBackground()
      .padding(12.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    GlideAsyncImage(
      imageUrl = gameModel.backgroundImage,
      contentDescription = "Game Cover",
      contentScale = ContentScale.Crop,
      modifier = Modifier
        .size(90.dp)
        .clip(RoundedCornerShape(12.dp))
    )

    SpacerH(16.dp)

    Column(
      modifier = Modifier.weight(1f)
    ) {
      val releaseText = gameModel.releaseYear
        ?: if (gameModel.isTba) "TBA" else "Unknown release date"
      val primaryGenre = gameModel.genres.firstOrNull()?.name
      val subtitle = listOfNotNull(releaseText, primaryGenre).joinToString(" • ")

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
      ) {
        Text(
          modifier = Modifier.weight(1f),
          text = gameModel.name,
          style = AppTheme.typography.title.copy(fontSize = 18.sp),
          color = AppTheme.colors.text
        )

        if (showAddButton) {
          val actionModifier = if (onActionClick != null) {
            Modifier.clickable { onActionClick() }
          } else {
            Modifier
          }
          Icon(
            imageVector = if (isSaved) Icons.Default.Delete else Icons.Default.Add,
            contentDescription = if (isSaved) "Remove from list" else "Add to list",
            tint = AppTheme.colors.text,
            modifier = actionModifier
              .size(24.dp)
              .padding(start = 8.dp),
          )
        }
      }
      SpacerV(4.dp)

      Text(
        text = subtitle,
        color = AppTheme.colors.secondaryText,
        fontSize = 14.sp
      )

      SpacerV(10.dp)

      Row(
        verticalAlignment = Alignment.CenterVertically
      ) {
/*
        PlatformIcon(Icons.Outlined.DesktopWindows)
        Spacer(modifier = Modifier.width(8.dp))
        PlatformIcon(Icons.Outlined.Gamepad)
        Spacer(modifier = Modifier.width(12.dp))
*/
        RatingComponent(
          modifier = Modifier
            .padding(horizontal = 10.dp, vertical = 2.dp),
          type = RatingComponentType.Small,
          value = gameModel.rating
        )
      }
    }
  }
}

@Composable
fun GameCardItemPlaceholder(
  modifier: Modifier = Modifier,
) {
  Row(
    modifier = modifier
      .fillMaxWidth()
      .gameHubRoundedBackground()
      .padding(12.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    Box(
      modifier = Modifier
        .size(90.dp)
        .shimmer()
    )

    SpacerH(16.dp)

    Column(
      modifier = Modifier.weight(1f)
    ) {
      Box(
        modifier = Modifier
          .fillMaxWidth(0.7f)
          .height(18.dp)
          .shimmer()
      )
      SpacerV(8.dp)
      Box(
        modifier = Modifier
          .fillMaxWidth(0.5f)
          .height(14.dp)
          .shimmer()
      )
      SpacerV(12.dp)
      Box(
        modifier = Modifier
          .width(80.dp)
          .height(22.dp)
          .shimmer()
      )
    }
  }
}