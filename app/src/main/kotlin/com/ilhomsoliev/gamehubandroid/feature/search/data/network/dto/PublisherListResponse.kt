package com.ilhomsoliev.gamehubandroid.feature.search.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PublisherListResponse(
  @SerialName("results")
  val publishers: List<PublisherDto>
)

@Serializable
data class PublisherDto(
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