package com.ilhomsoliev.gamehubandroid.core.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.ilhomsoliev.gamehubandroid.core.ui.theme.AppTheme

@Composable
fun Modifier.applyCardBackground() =
  this.then(
    Modifier
      .clip(RoundedCornerShape(AppTheme.shapes.cardRadius))
      .background(AppTheme.colors.surfaceLow)
      .padding(12.dp)
  )
