package com.ilhomsoliev.gamehubandroid.core.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ilhomsoliev.gamehubandroid.core.ui.modifier.gameHubRoundedBackground
import com.ilhomsoliev.gamehubandroid.core.ui.theme.AppTheme
import com.ilhomsoliev.gamehubandroid.feature.game.domain.StoreModel

@Composable
fun StoreComponent(
  modifier: Modifier = Modifier,
  storeModel: StoreModel,
) {
  Box(
    modifier = modifier
      .gameHubRoundedBackground()
      .padding(12.dp),
    contentAlignment = Alignment.Center
  ) {
    Text(
      text = storeModel.name,
      style = AppTheme.typography.bodyLarge,
      color = AppTheme.colors.onSurface
    )
  }
}