package com.ilhomsoliev.gamehubandroid.feature.game.data.dto.platform

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequirementsDto(
  @SerialName("minimum")
  val minimum: String? = null,
  @SerialName("recommended")
  val recommended: String? = null,
)