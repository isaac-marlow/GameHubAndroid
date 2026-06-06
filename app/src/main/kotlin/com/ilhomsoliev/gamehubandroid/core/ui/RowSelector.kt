package com.ilhomsoliev.gamehubandroid.core.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEachIndexed
import com.ilhomsoliev.gamehubandroid.core.ui.theme.AppTheme

@Composable
fun RowSelector(
  modifier: Modifier = Modifier,
  elements: List<String>,
  pickedElement: String,
  onElementPicked: (String) -> Unit,
) {
  Row(
    modifier = modifier
      .applyBackground(
        AppTheme.colors.surfaceBright,
        CircleShape,
        0.dp
      )
  ) {
    elements.fastForEachIndexed { index, element ->
      val isSelected = (element == pickedElement)
      val isFirst = index == 0
      val isLast = (index == elements.lastIndex)
      val shape = RoundedCornerShape(
        topStart = if (isFirst) 50.dp else 0.dp,
        bottomStart = if (isFirst) 50.dp else 0.dp,
        topEnd = if (isLast) 50.dp else 0.dp,
        bottomEnd = if (isLast) 50.dp else 0.dp,
      )

      Box(
        modifier = Modifier
          .weight(1f)
          .clip(shape)
          .background(if (isSelected) AppTheme.colors.primary else AppTheme.colors.surfaceLow),
        contentAlignment = Alignment.Center
      ) {
        Text(
          modifier = Modifier.padding(8.dp),
          text = element,
          style = AppTheme.typography.bodyMedium,
          color = if (isSelected) AppTheme.colors.onPrimary else AppTheme.colors.onSurfaceVar
        )
      }
    }
  }
}