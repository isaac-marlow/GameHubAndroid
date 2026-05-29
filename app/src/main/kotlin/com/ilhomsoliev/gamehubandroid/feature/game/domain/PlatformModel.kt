package com.ilhomsoliev.gamehubandroid.feature.game.domain

data class PlatformModel(
  val id: Int,
  val name: String,
  val slug: String,
  val releasedAt: String?,
  val requirements: RequirementsModel?,
)