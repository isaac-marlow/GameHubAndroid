package com.ilhomsoliev.gamehubandroid.feature.search.data.network.dto

import com.ilhomsoliev.gamehubandroid.feature.game.data.dto.platform.PlatformDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlatformListResponse(
  @SerialName("results")
  val platforms: List<PlatformDto>
)