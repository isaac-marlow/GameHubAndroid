package com.ilhomsoliev.gamehubandroid.feature.library.presentation

import com.ilhomsoliev.gamehubandroid.feature.game.domain.GameModel

sealed class LibraryUiState {
  object Loading : LibraryUiState()
  data class Content(val games: List<GameModel>) : LibraryUiState()
  object Empty : LibraryUiState()
  object Error : LibraryUiState()
}