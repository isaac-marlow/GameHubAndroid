package com.ilhomsoliev.gamehubandroid.feature.settings.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Language
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ilhomsoliev.gamehubandroid.core.ui.ContentBlock
import com.ilhomsoliev.gamehubandroid.core.ui.RowSelector
import com.ilhomsoliev.gamehubandroid.core.ui.icons.PaletteIcon
import com.ilhomsoliev.gamehubandroid.core.ui.modifier.applyCardBackground
import com.ilhomsoliev.gamehubandroid.core.ui.slot.GHSlot

@Composable
fun AppearanceSettingsBlock() {
  ContentBlock(title = "Appearance") {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .applyCardBackground()
    ) {
      GHSlot(
        modifier = Modifier.fillMaxWidth(),
        icon = PaletteIcon,
        text = "Theme",
        hasDivider = true,
        subcontent = {
          RowSelector(
            modifier = Modifier.padding(start = 36.dp),
            elements = listOf("System", "Light", "Dark"),
            pickedElement = "System",
            onElementPicked = {

            }
          )
        }
      )

      GHSlot(
        modifier = Modifier.fillMaxWidth(),
        icon = Icons.Default.Language,
        text = "App Language",
        subtitle = "English (US)",
        hasDivider = true,
        actionIcon = Icons.AutoMirrored.Filled.ArrowForwardIos
      )

      GHSlot(
        modifier = Modifier.fillMaxWidth(),
        icon = Icons.Default.GridView,
        text = "Display Layout",
        hasDivider = false,
        subcontent = {
          RowSelector(
            modifier = Modifier.padding(start = 36.dp),
            elements = listOf("Grid", "List"),
            pickedElement = "Grid",
            onElementPicked = {

            }
          )
        }
      )
    }
  }
}
