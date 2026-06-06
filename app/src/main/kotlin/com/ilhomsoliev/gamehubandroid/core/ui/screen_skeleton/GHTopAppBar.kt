package com.ilhomsoliev.gamehubandroid.core.ui.screen_skeleton

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.ilhomsoliev.gamehubandroid.core.ui.theme.AppTheme
import com.ilhomsoliev.gamehubandroid.core.ui.theme.AppThemeRoot

@Composable
@Preview
fun GHTopAppBarPreview() {
  AppThemeRoot(isDark = false) {
    GHTopAppBar(
      title = "Profile",
      actions = {
        Icon(imageVector = Icons.Default.Settings, contentDescription = "")
      })
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GHTopAppBar(
  title: String,
  actions: @Composable RowScope.() -> Unit = {},
) {
  TopAppBar(
    colors = TopAppBarColors(
      containerColor = AppTheme.colors.surface,
      titleContentColor = AppTheme.colors.onSurface,
      actionIconContentColor = AppTheme.colors.onSurface,
      navigationIconContentColor = AppTheme.colors.onSurface,
      scrolledContainerColor = AppTheme.colors.surface
    ),
    title = {
      Text(
        text = title,
        style = AppTheme.typography.headlineSmall,
        color = AppTheme.colors.primary
      )
    },
    actions = actions
  )
}