package com.ilhomsoliev.gamehubandroid.core.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val LightColors = AppColors(
  background = Color(0xFFF5F5F7),
  text = Color(0xFF1D1D1F),
  card = Color(0xFFFFFFFF),
  primaryText = Color(0xFFFFFFFF),
  secondary = Color(0xFFF0F0F5),
  secondaryText = Color(0xFF1D1D1F),

  primary = Color(0xFF84d7ac),
  surface = Color(0xFF121416),
  surfaceLow = Color(0xFF1a1c1e),
  surfaceBright = Color(0xFF37393b),
  onSurface = Color(0xFFe2e2e5),
  onSurfaceVar = Color(0xFFbec9c0),
  onPrimary = Color(0xFF003823),
)

val DarkColors = AppColors(
  background = Color(0xFF121212),
  text = Color(0xFFE8E8E8),
  card = Color(0xFF1E1E1E),
  primary = Color(0xFF39FF14),
  primaryText = Color(0xFF121212),
  secondary = Color(0xFF2A2A2A),
  secondaryText = Color(0xFFE8E8E8),

  // TODO
  surface = Color(0xFF121416),
  surfaceLow = Color(0xFF1a1c1e),
  surfaceBright = Color(0xFF37393b),
  onSurface = Color(0xFFe2e2e5),
  onSurfaceVar = Color(0xFFbec9c0),
  onPrimary = Color(0xFF003823),
)

val DefaultTypography = AppTypography(
  body = TextStyle(
    fontSize = 16.sp,
    fontFamily = FontFamily.SansSerif,
    fontWeight = FontWeight.Normal
  ),
  title = TextStyle(
    fontSize = 20.sp,
    fontFamily = FontFamily.SansSerif,
    fontWeight = FontWeight.Medium
  ),
  subtitle = TextStyle(
    fontSize = 18.sp,
    fontFamily = FontFamily.SansSerif,
    fontWeight = FontWeight.Medium
  ),
  label = TextStyle(
    fontSize = 16.sp,
    fontFamily = FontFamily.SansSerif,
    fontWeight = FontWeight.Medium
  ),
  button = TextStyle(
    fontSize = 16.sp,
    fontFamily = FontFamily.SansSerif,
    fontWeight = FontWeight.Medium
  ),
  caption = TextStyle(
    fontSize = 14.sp,
    fontFamily = FontFamily.SansSerif,
    fontWeight = FontWeight.Normal
  ),

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
  radiusSm = 6f,
  radiusMd = 8f,
  radiusLg = 10f,
  radiusXl = 14f
)