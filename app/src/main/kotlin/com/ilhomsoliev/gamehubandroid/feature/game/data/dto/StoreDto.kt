package com.ilhomsoliev.gamehubandroid.feature.game.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StoreWrapperDto(
  @SerialName("store")
  val store: StoreDto
)

@Serializable
data class StoreDto( // ✅
  @SerialName("id")
  val id: Int,
  @SerialName("name")
  val name: String,
  @SerialName("slug")
  val slug: String
)
