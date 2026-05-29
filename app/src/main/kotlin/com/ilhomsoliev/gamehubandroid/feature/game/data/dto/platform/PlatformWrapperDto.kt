package com.ilhomsoliev.gamehubandroid.feature.game.data.dto.platform

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class PlatformWrapperDto(// ✅
  @SerialName("platform")
  val platform: PlatformDto? = null,

  @SerialName("released_at")
  val releasedAt: String? = null,

  @SerialName("requirements")
  val requirements: RequirementsDto? = null
)