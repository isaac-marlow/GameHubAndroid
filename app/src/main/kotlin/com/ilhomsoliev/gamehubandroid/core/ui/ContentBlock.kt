package com.ilhomsoliev.gamehubandroid.core.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.ilhomsoliev.gamehubandroid.core.ui.theme.AppTheme

@Composable
fun ContentBlock(
  title: String,
  content: @Composable () -> Unit
) {
  Text(
    text = title,
    style = AppTheme.typography.headlineSmall, color = AppTheme.colors.onSurface,
  )
  SpacerV(16.dp)
  content.invoke()
}