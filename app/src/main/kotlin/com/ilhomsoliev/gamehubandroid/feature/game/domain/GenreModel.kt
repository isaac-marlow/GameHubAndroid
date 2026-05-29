package com.ilhomsoliev.gamehubandroid.feature.game.domain

data class GenreModel(
  val id: Int,
  val name: String,
  val slug: String,
  val imageBackground: String?,
  val gamesCount: Int,
)
