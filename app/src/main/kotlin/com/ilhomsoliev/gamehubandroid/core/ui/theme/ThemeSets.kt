package com.ilhomsoliev.gamehubandroid.core.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val LightColors = AppColors(
  primary = Color(0xFF006b44),
  secondary = Color(0xFF4d6357),
  surface = Color(0xFFfbfdf9),
  surfaceLow = Color(0xFFF0f5f1),
  surfaceBright = Color(0xFFffffff),
  onSurface = Color(0xFF191C1A),
  onSurfaceVar = Color(0xFFF0F5f1),
  onPrimary = Color(0xFFFFFFFF),
)

val DarkColors = AppColors(
  secondary = Color(0xFF214d42),
  primary = Color(0xFF84d7ac),
  surface = Color(0xFF121416),
  surfaceLow = Color(0xFF1a1c1e),
  surfaceBright = Color(0xFF37393b),
  onSurface = Color(0xFFe2e2e5),
  onSurfaceVar = Color(0xFFbec9c0),
  onPrimary = Color(0xFF003823),
)

val DefaultTypography = AppTypography(
  headlineSmall = TextStyle(
    fontSize = 24.sp,
    fontFamily = FontFamily.SansSerif,
    fontWeight = FontWeight.Bold
  ),
  titleLarge = TextStyle(
    fontSize = 20.sp,
    fontFamily = FontFamily.SansSerif,
    fontWeight = FontWeight.SemiBold
  ),
  bodyLarge = TextStyle(
    fontSize = 16.sp,
    fontFamily = FontFamily.SansSerif,
    fontWeight = FontWeight.Normal
  ),
  bodyMedium = TextStyle(
    fontSize = 14.sp,
    fontFamily = FontFamily.SansSerif,
    fontWeight = FontWeight.Normal
  ),
  labelMedium = TextStyle(
    fontSize = 12.sp,
    fontFamily = FontFamily.SansSerif,
    fontWeight = FontWeight.Medium
  )
)

val DefaultShapes = AppShapes(
  cardRadius = 32f,
  radiusXs = 4f,
  radiusSm = 8f,
  radiusMd = 12f,
  radiusLg = 16f,
  radiusXl = 24f,
  radiusFull = 9999f,
  radiusNone = 0f
)