package com.ilhomsoliev.gamehubandroid.core.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf

val LocalColors = compositionLocalOf<AppColors> { error("Missing Colors") }
val LocalTypography = compositionLocalOf<AppTypography> { error("Missing Typography") }
val LocalShapes = compositionLocalOf<AppShapes> { error("Missing Shapes") }

object AppTheme {
  val colors: AppColors
    @Composable @ReadOnlyComposable get() = LocalColors.current

  val typography: AppTypography
    @Composable @ReadOnlyComposable get() = LocalTypography.current

  val shapes: AppShapes
    @Composable @ReadOnlyComposable get() = LocalShapes.current
}