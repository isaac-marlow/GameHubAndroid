package com.ilhomsoliev.gamehubandroid.core.ui.modifier

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ilhomsoliev.gamehubandroid.core.ui.theme.AppTheme

@Composable
fun Modifier.gameHubRoundedBackground(
  backgroundColor: Color = AppTheme.colors.card,
  borderStrokeColor: Color = AppTheme.colors.secondary,
): Modifier {
  val shape = RoundedCornerShape(AppTheme.shapes.cardRadius)
  return this
    .background(color = backgroundColor, shape = shape)
    .then(
      Modifier.border(
        border = BorderStroke(1.dp, AppTheme.colors.secondary),
        shape = shape
      )
    )
    .clip(shape)
}