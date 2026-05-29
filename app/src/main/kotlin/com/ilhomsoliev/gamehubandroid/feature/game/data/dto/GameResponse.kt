package com.ilhomsoliev.gamehubandroid.feature.game.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GameResponse(
  @SerialName("count")
  val count: Int,
  @SerialName("next")
  val nextUrl: String?,
  @SerialName("previous")
  val previousUrl: String?,
  @SerialName("results")
  val games: List<GameDto>
)
