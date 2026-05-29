package com.ilhomsoliev.gamehubandroid.core.ui.modifier

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp
import com.ilhomsoliev.gamehubandroid.core.ui.theme.AppTheme

fun Modifier.shimmer(
  isLoading: Boolean = true,
  shape: Shape = RoundedCornerShape(12.dp),
  baseColor: Color? = null,
  highlightColor: Color? = null,
  durationMillis: Int = 1100,
): Modifier = composed {
  if (!isLoading) return@composed this

  val transition = rememberInfiniteTransition(label = "shimmer")
  val progress by transition.animateFloat(
    initialValue = 0f,
    targetValue = 1f,
    animationSpec = infiniteRepeatable(
      animation = tween(durationMillis, easing = LinearEasing),
    ),
    label = "shimmerProgress"
  )

  val resolvedBase = baseColor ?: AppTheme.colors.secondary
  val resolvedHighlight = highlightColor ?: resolvedBase.copy(alpha = 0.45f)

  this
    .clip(shape)
    .drawWithCache {
      val width = size.width
      val height = size.height
      val gradientWidth = width * 0.6f
      val startX = (-gradientWidth) + (width + gradientWidth * 2) * progress
      val endX = startX + gradientWidth
      val brush = Brush.linearGradient(
        colors = listOf(resolvedBase, resolvedHighlight, resolvedBase),
        start = Offset(startX, 0f),
        end = Offset(endX, height)
      )

      onDrawWithContent {
        drawContent()
        drawRect(brush = brush)
      }
    }
}
