package com.ilhomsoliev.gamehubandroid.core.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

// TODO Add as you go
@Immutable
data class AppColors(
  val primary: Color,
  val secondary: Color,
  val surface: Color,
  val surfaceLow: Color,
  val surfaceBright: Color,
  // Text
  val onSurface: Color,
  val onSurfaceVar: Color,
  val onPrimary: Color,
  // Other
  val background: Color,
  val text: Color,
  val card: Color,
  val primaryText: Color,
  val secondaryText: Color,

)

@Immutable
data class AppTypography(
  val headlineSmall: TextStyle,
  val titleLarge: TextStyle,
  val bodyLarge: TextStyle,
  val bodyMedium: TextStyle,
  val labelMedium: TextStyle,
  // Other
  val body: TextStyle,
  val title: TextStyle,
  val subtitle: TextStyle,
  val label: TextStyle,
  val button: TextStyle,
  val caption: TextStyle
)

@Immutable
data class AppShapes(
  val cardRadius: Float,
  val radiusSm: Float,
  val radiusMd: Float,
  val radiusLg: Float,
  val radiusXl: Float
)