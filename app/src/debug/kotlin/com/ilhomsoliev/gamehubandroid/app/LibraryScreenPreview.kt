package com.ilhomsoliev.gamehubandroid.app

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.ilhomsoliev.gamehubandroid.core.ui.theme.AppThemeRoot
import com.ilhomsoliev.gamehubandroid.feature.game.domain.GameModel
import com.ilhomsoliev.gamehubandroid.feature.library.presentation.LibraryContent
import com.ilhomsoliev.gamehubandroid.feature.library.presentation.LibraryUiState

class LibraryPreviewProvider : PreviewParameterProvider<LibraryUiState> {
  override val values: Sequence<LibraryUiState>
    get() = sequenceOf(
      LibraryUiState.Content(listOf(GameModel.sample)),
      LibraryUiState.Empty,
      LibraryUiState.Error,
      LibraryUiState.Loading,
    )
}

@Composable
@Preview
fun LibraryScreenPreview(
  @PreviewParameter(LibraryPreviewProvider::class)
  state: LibraryUiState
) {
  AppThemeRoot(true) {
    LibraryContent(state) { }
  }
}

