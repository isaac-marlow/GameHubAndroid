package com.ilhomsoliev.gamehubandroid.feature.game.data.dto.platform

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlatformDto(// ✅
  @SerialName("id")
  val id: Int,
  @SerialName("name")
  val name: String,
  @SerialName("slug")
  val slug: String
)
