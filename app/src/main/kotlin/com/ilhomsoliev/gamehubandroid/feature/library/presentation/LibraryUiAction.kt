package com.ilhomsoliev.gamehubandroid.feature.library.presentation

sealed class LibraryUiAction {
  data class OpenGameDetail(val gameId: Int) : LibraryUiAction()
  data class RemoveGame(val gameId: Int) : LibraryUiAction()
  object Refresh : LibraryUiAction()
  object OpenExplore : LibraryUiAction()

}