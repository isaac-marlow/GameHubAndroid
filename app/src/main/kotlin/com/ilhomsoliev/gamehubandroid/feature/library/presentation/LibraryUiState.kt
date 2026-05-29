package com.ilhomsoliev.gamehubandroid.feature.library.presentation

import com.ilhomsoliev.gamehubandroid.feature.game.domain.GameModel

data class LibraryUiState(
  val games: List<GameModel> = emptyList(),
  val isLoading: Boolean = false,
)
