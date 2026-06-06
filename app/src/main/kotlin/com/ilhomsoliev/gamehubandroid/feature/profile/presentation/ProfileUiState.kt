package com.ilhomsoliev.gamehubandroid.feature.profile.presentation

data class ProfileUiState(
  val name: String = "",
  val username: String = "",
  val bio: String = "",
  val avatar: String = "",
  val libraryStatistics: LibraryStatisticsUiState = LibraryStatisticsUiState(),
  val savedPosts: Int = 0,
) {
  data class LibraryStatisticsUiState(
    val totalGames: Int = 0,
    val completed: Int = 0,
    val currentlyPlaing: Int = 0,
    val favorites: Int = 0,
    val wishlist: Int = 0,
    val dropped: Int = 0,
  )

}