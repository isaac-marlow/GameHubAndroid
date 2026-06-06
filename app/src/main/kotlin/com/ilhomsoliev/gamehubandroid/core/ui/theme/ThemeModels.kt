package com.ilhomsoliev.gamehubandroid.core.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

@Immutable
data class AppColors(
  val primary: Color,
  val secondary: Color,
  val surface: Color,
  val surfaceLow: Color,
  val surfaceBright: Color,
  // Text colors
  val onSurface: Color,
  val onSurfaceVar: Color,
  val onPrimary: Color,
)

@Immutable
data class AppTypography(
  val headlineSmall: TextStyle,
  val titleLarge: TextStyle,
  val bodyLarge: TextStyle,
  val bodyMedium: TextStyle,
  val labelMedium: TextStyle,
)

@Immutable
data class AppShapes(
  val radiusNone: Float,
  val radiusXs: Float,
  val radiusSm: Float,
  val radiusMd: Float,
  val radiusLg: Float,
  val radiusXl: Float,
  val radiusFull: Float,
  // out of system design
  val cardRadius: Float,
)