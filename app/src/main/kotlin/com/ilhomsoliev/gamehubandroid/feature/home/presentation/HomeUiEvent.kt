package com.ilhomsoliev.gamehubandroid.feature.home.presentation

sealed interface HomeUiEvent {
  data class NavigateToGameDetail(val id: Int) : HomeUiEvent
  data class NavigateToSearch(
    val genreId: Int? = null,
    val openedFromHomeSearch: Boolean = false
  ) : HomeUiEvent
}
