package com.ilhomsoliev.gamehubandroid.feature.game.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenreDto( // ✅
  @SerialName("id")
  val id: Int,
  @SerialName("name")
  val name: String,
  @SerialName("slug")
  val slug: String,
  @SerialName("image_background")
  val imageBackground: String? = null,
  @SerialName("games_count")
  val gamesCount: Int = 0,
)

@Serializable
class GenresResponse(

  @SerialName("next")
  val nextUrl: String?,

  @SerialName("previous")
  val previousUrl: String?,

  @SerialName("count")
  val count: Int,

  @SerialName("results")
  val results: List<GenreDto>
)