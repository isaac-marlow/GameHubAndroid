package com.ilhomsoliev.gamehubandroid.feature.settings

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ilhomsoliev.gamehubandroid.core.ui.SpacerV
import com.ilhomsoliev.gamehubandroid.core.ui.screen_skeleton.GHTopAppBar
import com.ilhomsoliev.gamehubandroid.core.ui.theme.AppTheme
import com.ilhomsoliev.gamehubandroid.feature.settings.components.AboutBlock
import com.ilhomsoliev.gamehubandroid.feature.settings.components.AppearanceSettingsBlock
import com.ilhomsoliev.gamehubandroid.feature.settings.components.DataManagementBlock
import com.ilhomsoliev.gamehubandroid.feature.settings.components.PrivacySecurityBlock
import org.koin.androidx.compose.koinViewModel

@Composable
fun SettingsScreen(
  vm: SettingsViewModel = koinViewModel(),
  goBack: () -> Unit
) {
  val state by vm.uiState.collectAsState()

  SettingsContent(
    state
  ) {
    when (it) {
      SettingsUiAction.OnBack -> goBack()
    }
  }
}

@Composable
fun SettingsContent(
  state: SettingsUiState,
  onAction: (SettingsUiAction) -> Unit
) {
  Scaffold(
    containerColor = AppTheme.colors.surface,
    topBar = {
      GHTopAppBar(
        title = "Profile",
        navigation = {
          IconButton(onClick = {
            onAction(SettingsUiAction.OnBack)
          }) {
            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBackIos, contentDescription = "")
          }
        })
    }
  ) { paddingValues ->
    LazyColumn(
      modifier = Modifier
        .fillMaxWidth()
        .padding(paddingValues)
        .padding(horizontal = 16.dp)
    ) {
      item {
        AppearanceSettingsBlock()
        SpacerV(12.dp)
      }
      item {
        DataManagementBlock()
        SpacerV(12.dp)
      }
      item {
        PrivacySecurityBlock()
        SpacerV(12.dp)
      }
      item {
        AboutBlock()
      }
    }
  }
}