package com.ilhomsoliev.gamehubandroid.feature.game.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ScreenshotDto( // ✅
  @SerialName("id")
  val id: Int,

  @SerialName("image")
  val image: String, // The URL to the generic image

  @SerialName("width")
  val width: Int,

  @SerialName("height")
  val height: Int,

  @SerialName("is_deleted")
  val isDeleted: Boolean = false
)

@Serializable
data class ScreenshotsResponse(
  @SerialName("count")
  val count: Int,
  @SerialName("results")
  val results: List<ScreenshotDto>
)
