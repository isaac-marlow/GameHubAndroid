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
  primary = Color(0xFF00B300),
  primaryText = Color(0xFFFFFFFF),
  secondary = Color(0xFFF0F0F5),
  secondaryText = Color(0xFF1D1D1F),
)

val DarkColors = AppColors(
  background = Color(0xFF121212),
  text = Color(0xFFE8E8E8),
  card = Color(0xFF1E1E1E),
  primary = Color(0xFF39FF14),
  primaryText = Color(0xFF121212),
  secondary = Color(0xFF2A2A2A),
  secondaryText = Color(0xFFE8E8E8),
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
  )
)

val DefaultShapes = AppShapes(
  cardRadius = 32f,
  radiusSm = 6f,
  radiusMd = 8f,
  radiusLg = 10f,
  radiusXl = 14f
)