package com.ilhomsoliev.gamehubandroid.feature.home.presentation

import com.ilhomsoliev.gamehubandroid.feature.game.domain.GameModel
import com.ilhomsoliev.gamehubandroid.feature.game.domain.GenreModel

data class HomeUiState(
  val hasError: Boolean = false,
  val isLoading: Boolean = true,
  val isLoadingNewReleasesNext: Boolean = false,
  val isLoadingTopRatedNext: Boolean = false,
  val isLoadingGenresNext: Boolean = false,
  val gameOfTheDay: GameModel? = null,
  val searchQuery: String = "",
  val newReleases: List<GameModel>? = null,
  val topRated: List<GameModel>? = null,
  val genres: List<GenreModel>? = null,
)