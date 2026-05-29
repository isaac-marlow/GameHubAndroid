package com.ilhomsoliev.gamehubandroid.feature.search.data.network.dto

import com.ilhomsoliev.gamehubandroid.feature.game.data.dto.StoreDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StoreListResponse(
  @SerialName("results")
  val stores: List<StoreDto>
)