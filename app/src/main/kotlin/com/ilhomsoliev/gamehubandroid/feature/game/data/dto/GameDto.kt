package com.ilhomsoliev.gamehubandroid.feature.game.data.dto

import com.ilhomsoliev.gamehubandroid.feature.game.data.dto.platform.PlatformWrapperDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GameDto(
  @SerialName("id")
  val id: Int,// ✅

  @SerialName("slug")
  val slug: String,// ✅

  @SerialName("name")
  val name: String,// ✅

  @SerialName("released")
  val releasedDate: String? = null,// ✅

  @SerialName("tba") // is to be announced // ✅
  val isTba: Boolean = false,

  @SerialName("background_image")// ✅
  val backgroundImage: String? = null,

  @SerialName("rating")// ✅
  val rating: Double = 0.0,

  @SerialName("description") // ✅
  val description: String? = null,

  @SerialName("reddit_description")// ✅
  val redditDescription: String? = null,

  @SerialName("esrb_rating") // ✅
  val esrbRating: EsrbRatingDto? = null,

  @SerialName("platforms")
  val platforms: List<PlatformWrapperDto>? = null, // ✅

  @SerialName("stores")
  val stores: List<StoreWrapperDto>? = null, // ✅

  @SerialName("genres")
  val genres: List<GenreDto>? = null, // ✅

  @SerialName("tags") // ✅
  val tags: List<TagDto>? = null
)
