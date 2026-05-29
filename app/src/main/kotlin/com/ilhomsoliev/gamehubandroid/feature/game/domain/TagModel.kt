package com.ilhomsoliev.gamehubandroid.feature.game.domain

data class TagModel(
  val id: Int,
  val name: String,
  val slug: String,
  val language: String?,
  val gamesCount: Int,
  val imageBackground: String?
)
