package com.ilhomsoliev.gamehubandroid.core.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ilhomsoliev.gamehubandroid.core.ui.modifier.applyClickIfNeeded
import com.ilhomsoliev.gamehubandroid.core.ui.modifier.gameHubRoundedBackground
import com.ilhomsoliev.gamehubandroid.core.ui.theme.AppTheme

@Composable
fun GHChip(
  modifier: Modifier = Modifier,
  text: String,
  isActive: Boolean = false,
  onClick: (() -> Unit)? = null
) {
  val backgroundColor = if (isActive) AppTheme.colors.primary else AppTheme.colors.secondary
  val textColor = if (isActive) AppTheme.colors.onPrimary else AppTheme.colors.onSurface

  Box(
    modifier = modifier
      .gameHubRoundedBackground(
        backgroundColor = backgroundColor
      )
      .applyClickIfNeeded(onClick)
  ) {
    Text(
      modifier = Modifier
        .padding(6.dp),
      text = text,
      style = AppTheme.typography.bodyMedium,
      color = textColor
    )
  }
}