package com.ilhomsoliev.gamehubandroid.feature.game.domain

data class PagedGamesResult(
  val games: List<GameModel>,
  val nextUrl: String?
)

data class PagedGenresResult(
  val genres: List<GenreModel>,
  val nextUrl: String?
)
