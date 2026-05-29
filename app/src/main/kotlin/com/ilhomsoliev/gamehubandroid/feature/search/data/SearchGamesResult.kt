package com.ilhomsoliev.gamehubandroid.feature.search.data

import com.ilhomsoliev.gamehubandroid.feature.game.domain.GameModel

data class SearchGamesResult(
  val games: List<GameModel>,
  val nextUrl: String?
)
