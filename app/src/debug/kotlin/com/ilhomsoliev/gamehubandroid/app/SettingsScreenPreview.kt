package com.ilhomsoliev.gamehubandroid.app

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.ilhomsoliev.gamehubandroid.core.ui.theme.AppThemeRoot
import com.ilhomsoliev.gamehubandroid.feature.settings.SettingsContent
import com.ilhomsoliev.gamehubandroid.feature.settings.SettingsUiState

class SettingsPreviewProvider : PreviewParameterProvider<SettingsUiState> {
  override val values: Sequence<SettingsUiState>
    get() = sequenceOf(
      SettingsUiState()
    )
}

@Composable
@Preview
fun SettingsScreenPreview(
  @PreviewParameter(SettingsPreviewProvider::class)
  state: SettingsUiState
) {
  AppThemeRoot(false) {
    SettingsContent(state) { }
  }
}