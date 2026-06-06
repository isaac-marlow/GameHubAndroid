package com.ilhomsoliev.gamehubandroid.core.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ilhomsoliev.gamehubandroid.core.ui.theme.AppTheme

@Composable
fun Modifier.applyCardBackground() =
  this.then(
    Modifier
      .applyBackground(
        color = AppTheme.colors.surfaceLow,
        shape = RoundedCornerShape(AppTheme.shapes.cardRadius),
        padding = 12.dp
      )
  )

@Composable
fun Modifier.applyBackground(
  color: androidx.compose.ui.graphics.Color,
  shape: RoundedCornerShape,
  padding: Dp = 0.dp
) =
  this.then(
    Modifier
      .clip(shape)
      .background(color)
      .border(
        0.1.dp,
        AppTheme.colors.onSurfaceVar,
        shape
      )
      .padding(padding)
  )

