package com.ilhomsoliev.gamehubandroid.feature.home.presentation

sealed interface HomeUiAction {

  object OpenSearch : HomeUiAction
  class OpenGameDetail(val id: Int) : HomeUiAction
  class OpenGenreDetail(val id: Int) : HomeUiAction
  data object LoadNextNewReleases : HomeUiAction
  data object LoadNextTopRated : HomeUiAction
  data object LoadNextGenres : HomeUiAction
}