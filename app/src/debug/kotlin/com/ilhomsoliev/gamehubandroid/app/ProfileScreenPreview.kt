package com.ilhomsoliev.gamehubandroid.app

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.ilhomsoliev.gamehubandroid.core.ui.theme.AppThemeRoot
import com.ilhomsoliev.gamehubandroid.feature.profile.presentation.ProfileContent
import com.ilhomsoliev.gamehubandroid.feature.profile.presentation.ProfileUiState

class ProfileScreenPreviewProvider : PreviewParameterProvider<ProfileUiState> {
  override val values: Sequence<ProfileUiState>
    get() = sequenceOf(
      ProfileUiState(
        name = "Ilhom Soliev",
        bio = "Hello my lorem impusm some text more of the text here take it more name is something android developer",
      )
    )

}

@Preview
@Composable
fun ProfileScreenPreview(
  @PreviewParameter(ProfileScreenPreviewProvider::class)
  state: ProfileUiState,
) {
  AppThemeRoot(false) {
    ProfileContent(state) { }
  }
}