package com.ilhomsoliev.gamehubandroid.feature.game.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EsrbRatingDto(// ✅
  @SerialName("id")
  val id: String,
  @SerialName("name")
  val name: String,
  @SerialName("slug")
  val slug: String
)