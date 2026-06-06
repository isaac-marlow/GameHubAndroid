package com.ilhomsoliev.gamehubandroid.feature.settings.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ilhomsoliev.gamehubandroid.core.ui.ContentBlock
import com.ilhomsoliev.gamehubandroid.core.ui.applyCardBackground
import com.ilhomsoliev.gamehubandroid.core.ui.slot.GHSlot

@Composable
fun AboutBlock(modifier: Modifier = Modifier) {
  ContentBlock("About") {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .applyCardBackground()
    ) {
      GHSlot(
        modifier = Modifier.fillMaxWidth(),
        icon = Icons.Default.Info,
        text = "App version",
        subtitle = "1.0.0",
        hasDivider = false,
      )
    }
  }
}