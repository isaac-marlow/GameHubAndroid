package com.ilhomsoliev.gamehubandroid.feature.profile.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.ilhomsoliev.gamehubandroid.core.ui.ContentBlock
import com.ilhomsoliev.gamehubandroid.core.ui.SpacerH
import com.ilhomsoliev.gamehubandroid.core.ui.SpacerV
import com.ilhomsoliev.gamehubandroid.core.ui.modifier.applyCardBackground
import com.ilhomsoliev.gamehubandroid.core.ui.theme.AppTheme

fun LazyListScope.ProfileLibraryStatisticsBlock(
  completed: Int,
  currentlyPlaying: Int,
  favorites: Int,
  wishlist: Int,
) {
  item {
    ContentBlock(title = "Library Statistics") {
      Row(modifier = Modifier.fillMaxWidth()) {
        Stat(
          modifier = Modifier.weight(1f),
          label = "Completed",
          icon = Icons.Default.DoneAll,
          stat = completed
        )
        SpacerH(8.dp)
        Stat(
          modifier = Modifier.weight(1f),
          label = "Currently Playing",
          icon = Icons.Default.PlayCircle,
          stat = currentlyPlaying
        )
      }
      SpacerV(8.dp)
      Row(modifier = Modifier.fillMaxWidth()) {
        Stat(
          modifier = Modifier.weight(1f),
          label = "Favorites",
          icon = Icons.Default.FavoriteBorder,
          stat = favorites
        )
        SpacerH(8.dp)
        Stat(
          modifier = Modifier.weight(1f),
          label = "Wishlist",
          icon = Icons.Default.Bookmark,
          stat = wishlist
        )
      }
    }
  }
}

@Composable
private fun Stat(
  modifier: Modifier = Modifier,
  label: String,
  icon: ImageVector,
  stat: Int,
) {
  Column(modifier = modifier.applyCardBackground()) {
    Icon(icon, contentDescription = "", tint = AppTheme.colors.onSurface)
    SpacerV(8.dp)
    Text(
      text = stat.toString(),
      style = AppTheme.typography.headlineSmall,
      color = AppTheme.colors.onSurface
    )
    SpacerV(8.dp)
    Text(
      text = label,
      style = AppTheme.typography.bodyMedium,
      color = AppTheme.colors.onSurfaceVar,
    )
  }

}