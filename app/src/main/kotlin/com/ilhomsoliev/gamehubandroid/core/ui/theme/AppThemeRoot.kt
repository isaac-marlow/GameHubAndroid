package com.ilhomsoliev.gamehubandroid.core.ui.theme

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.view.WindowManager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

@Composable
fun AppThemeRoot(
  isDark: Boolean,
  content: @Composable () -> Unit
) {
  val colors = if (isDark) DarkColors else LightColors

  CompositionLocalProvider(
    LocalColors provides colors,
    LocalTypography provides DefaultTypography,
    LocalShapes provides DefaultShapes
  ) {
    UpdateStatusBarColor()
    content()
  }
}

@Composable
private fun UpdateStatusBarColor() {
  val view = LocalView.current
  if (view.isInEditMode) return

  val backgroundColor = AppTheme.colors.background
  val useDarkIcons = backgroundColor.luminance() > 0.5f

  SideEffect {
    val window = view.context.findActivity()?.window ?: return@SideEffect
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    WindowCompat.setDecorFitsSystemWindows(window, false)
    window.statusBarColor = Color.Transparent.toArgb()
    window.navigationBarColor = Color.Transparent.toArgb()
    WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = useDarkIcons
    WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = useDarkIcons
  }
}

private fun Context.findActivity(): Activity? {
  var current = this
  while (current is ContextWrapper) {
    if (current is Activity) return current
    current = current.baseContext
  }
  return null
}