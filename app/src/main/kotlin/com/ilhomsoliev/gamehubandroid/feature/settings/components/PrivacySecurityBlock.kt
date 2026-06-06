package com.ilhomsoliev.gamehubandroid.feature.settings.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Policy
import androidx.compose.material.icons.filled.PrivacyTip
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ilhomsoliev.gamehubandroid.core.ui.ContentBlock
import com.ilhomsoliev.gamehubandroid.core.ui.modifier.applyCardBackground
import com.ilhomsoliev.gamehubandroid.core.ui.slot.GHSlot

@Composable
fun PrivacySecurityBlock() {
  ContentBlock("Privacy & Security") {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .applyCardBackground()
    ) {
      GHSlot(
        modifier = Modifier.fillMaxWidth(),
        icon = Icons.Default.PrivacyTip,
        text = "Export Library",
        hasDivider = true,
      )
      GHSlot(
        modifier = Modifier.fillMaxWidth(),
        icon = Icons.Default.Policy,
        text = "Export Library",
        hasDivider = false,
      )
    }
  }
}