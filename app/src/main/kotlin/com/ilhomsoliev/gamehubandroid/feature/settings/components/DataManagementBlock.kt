package com.ilhomsoliev.gamehubandroid.feature.settings.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ReceiptLong
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Upload
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ilhomsoliev.gamehubandroid.core.ui.ContentBlock
import com.ilhomsoliev.gamehubandroid.core.ui.applyCardBackground
import com.ilhomsoliev.gamehubandroid.core.ui.slot.GHSlot

@Composable
fun DataManagementBlock() {
  ContentBlock("Data Management") {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .applyCardBackground()
    ) {
      GHSlot(
        modifier = Modifier.fillMaxWidth(),
        icon = Icons.Default.Upload,
        text = "Export Library",
        hasDivider = true,
      )
      GHSlot(
        modifier = Modifier.fillMaxWidth(),
        icon = Icons.Default.Download,
        text = "Import Library",
        hasDivider = true,
      )
      GHSlot(
        modifier = Modifier.fillMaxWidth(),
        icon = Icons.Default.Delete,
        text = "Delete all Local Data",
        subtitle = "This action cannot be undone",
        hasDivider = true,
      )
      GHSlot(
        modifier = Modifier.fillMaxWidth(),
        icon = Icons.AutoMirrored.Filled.ReceiptLong,
        text = "Import Library",
        hasDivider = false,
      )
    }
  }
}