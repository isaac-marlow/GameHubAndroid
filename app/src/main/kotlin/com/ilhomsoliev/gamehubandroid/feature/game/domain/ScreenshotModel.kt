package com.ilhomsoliev.gamehubandroid.feature.game.domain

data class ScreenshotModel(
  val id: Int,
  val image: String,
  val width: Int,
  val height: Int,
  val isDeleted: Boolean
)