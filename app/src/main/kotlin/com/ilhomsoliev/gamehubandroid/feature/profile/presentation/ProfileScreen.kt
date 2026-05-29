package com.ilhomsoliev.gamehubandroid.feature.profile.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.ilhomsoliev.gamehubandroid.core.ui.theme.AppTheme

@Composable
fun ProfileScreen() {
  Box(
    modifier = Modifier
      .fillMaxSize()
      .background(AppTheme.colors.background),
    contentAlignment = Alignment.Center
  ) {
    Text(
      text = "Profile",
      style = AppTheme.typography.title,
      color = AppTheme.colors.text
    )
  }
}
