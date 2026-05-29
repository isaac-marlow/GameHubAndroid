package com.ilhomsoliev.gamehubandroid.core.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

// TODO Add as you go
@Immutable
data class AppColors(
  val background: Color,
  val text: Color,
  val card: Color,
  val primary: Color,
  val primaryText: Color,
  val secondary: Color,
  val secondaryText: Color,

)

@Immutable
data class AppTypography(
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