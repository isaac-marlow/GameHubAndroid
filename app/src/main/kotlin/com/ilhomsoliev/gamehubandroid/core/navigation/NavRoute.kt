package com.ilhomsoliev.gamehubandroid.core.navigation

import kotlinx.serialization.Serializable

sealed interface NavRoute {
  @Serializable
  object Splash : NavRoute

  @Serializable
  object Auth : NavRoute

  @Serializable
  object Home : NavRoute

  @Serializable
  data class Search(
    val genreId: Int? = null,
    val openedFromHomeSearch: Boolean = false
  ) : NavRoute

  @Serializable
  object Library : NavRoute

  @Serializable
  object Profile : NavRoute

  @Serializable
  data class GameDetail(val id: Int) : NavRoute

}