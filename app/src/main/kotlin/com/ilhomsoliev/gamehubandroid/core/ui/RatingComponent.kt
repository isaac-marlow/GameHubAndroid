package com.ilhomsoliev.gamehubandroid.core.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ilhomsoliev.gamehubandroid.core.ui.theme.AppTheme

enum class RatingComponentType {
  Small,
  Medium,
  Big;
}

@Composable
fun RatingComponent(
  modifier: Modifier = Modifier,
  type: RatingComponentType,
  value: String,
) {
  val innerPaddings = when (type) {
    RatingComponentType.Small -> 4.dp
    RatingComponentType.Medium -> 6.dp
    RatingComponentType.Big -> 8.dp
  }
  val fontSize = when (type) {
    RatingComponentType.Small -> 12.sp
    RatingComponentType.Medium -> 14.sp
    RatingComponentType.Big -> 16.sp
  }

  Box(
    modifier = modifier
      .clip(RoundedCornerShape(AppTheme.shapes.radiusXl))
      .background(AppTheme.colors.primary),
    contentAlignment = Alignment.Center,
  ) {
    Text(
      modifier = Modifier.padding(innerPaddings),
      text = value,
      style = AppTheme.typography.labelMedium.copy(fontSize = fontSize),
      color = AppTheme.colors.onPrimary
    )
  }
}