package com.ilhomsoliev.gamehubandroid.core.ui.modifier

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

fun Modifier.verticalGradientScrim(
  colors: List<Color> = listOf(
    Color.Transparent,
    Color.Black.copy(alpha = 0.7f)
  ),
  startY: Float = 0f,
  endY: Float = Float.POSITIVE_INFINITY
): Modifier = drawWithContent {
  drawContent()
  val actualEndY = if (endY.isFinite()) endY else size.height
  drawRect(
    brush = Brush.verticalGradient(
      colors = colors,
      startY = startY,
      endY = actualEndY
    )
  )
}
