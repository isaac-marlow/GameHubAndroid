package com.ilhomsoliev.gamehubandroid.feature.search.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DeveloperListResponse(
  @SerialName("results")
  val developers: List<DeveloperDto>
)

@Serializable
data class DeveloperDto(
  @SerialName("id")
  val id: Int,
  @SerialName("name")
  val name: String,
  @SerialName("slug")
  val slug: String,
  @SerialName("games_count")
  val gamesCount: Int = 0,
  @SerialName("image_background")
  val imageBackground: String? = null
)
