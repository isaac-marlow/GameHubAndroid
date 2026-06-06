package com.ilhomsoliev.gamehubandroid.feature.details.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ilhomsoliev.gamehubandroid.core.ui.GHChip
import com.ilhomsoliev.gamehubandroid.core.ui.SpacerV
import com.ilhomsoliev.gamehubandroid.core.ui.modifier.gameHubRoundedBackground
import com.ilhomsoliev.gamehubandroid.core.ui.theme.AppTheme
import com.ilhomsoliev.gamehubandroid.feature.game.domain.GenreModel
import com.ilhomsoliev.gamehubandroid.feature.game.domain.TagModel

@Composable
fun LazyItemScope.GameInfo(
  modifier: Modifier = Modifier,
  genres: List<GenreModel>,
  tags: List<TagModel>,
  esrbRating: String?
) {

  Column(
    modifier = modifier
      .fillMaxWidth()
      .padding(horizontal = 12.dp)
      .gameHubRoundedBackground()
      .padding(16.dp),
    horizontalAlignment = Alignment.Start,
    verticalArrangement = Arrangement.Center
  ) {
    Text(
      text = "Game Info",
      style = AppTheme.typography.titleLarge.copy(fontSize = 18.sp),
      color = AppTheme.colors.onSurface
    )
    SpacerV(16.dp)
    Row(modifier = Modifier.fillMaxWidth()) {
      Column(modifier = Modifier.weight(1f)) {
        Text(
          text = "Genres",
          style = AppTheme.typography.bodyMedium,
          color = AppTheme.colors.onSurface
        )
        FlowRow {
          genres.forEach {
            GHChip(
              modifier = Modifier.padding(end = 6.dp, top = 6.dp),
              text = it.name
            )
          }
        }

        SpacerV(16.dp)

        Text(
          text = "Tags",
          style = AppTheme.typography.bodyMedium,
          color = AppTheme.colors.onSurface
        )
        FlowRow {
          tags.take(5).forEach {
            GHChip(
              modifier = Modifier.padding(end = 6.dp, top = 6.dp),
              text = it.name
            )
          }
        }
      }
      Column(modifier = Modifier.weight(1f)) {
        Text(
          text = "ESRB Rating",
          style = AppTheme.typography.bodyMedium,
          color = AppTheme.colors.onSurface
        )

        SpacerV(12.dp)

        Text(
          text = esrbRating ?: "Empty",
          style = AppTheme.typography.bodyMedium,
          color = AppTheme.colors.onSurface
        )

      }
    }
  }
}