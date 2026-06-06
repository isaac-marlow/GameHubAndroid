package com.ilhomsoliev.gamehubandroid.core.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ilhomsoliev.gamehubandroid.core.ui.modifier.applyCardBackground
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
  val releaseText = gameModel.releaseYear
    ?: if (gameModel.isTba) "TBA" else "Unknown release date"
  val primaryGenre = gameModel.genres.firstOrNull()?.name
  val subtitle =
    listOfNotNull(releaseText, primaryGenre, gameModel.rating + " ★").joinToString(" • ")

  Row(
    modifier = modifier
      .fillMaxWidth()
      .applyCardBackground(),
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
      Text(
        text = gameModel.name,
        style = AppTheme.typography.titleLarge,
        color = AppTheme.colors.onSurface
      )

      SpacerV(8.dp)

      Text(
        text = subtitle,
        color = AppTheme.colors.onSurface,
        fontSize = 14.sp
      )
    }

    if (showAddButton) {
      IconButton(modifier = Modifier.padding(start = 8.dp), onClick = {
        onActionClick?.invoke()
      }) {
        Icon(
          imageVector = if (isSaved) Icons.Default.Delete else Icons.Default.Add,
          contentDescription = if (isSaved) "Remove from list" else "Add to list",
          tint = AppTheme.colors.onSurface,
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
      .applyCardBackground(),
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