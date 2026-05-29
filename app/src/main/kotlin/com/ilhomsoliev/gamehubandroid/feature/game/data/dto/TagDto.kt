package com.ilhomsoliev.gamehubandroid.feature.game.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TagDto( // ✅
  @SerialName("id")
  val id: Int,

  @SerialName("name")
  val name: String,

  @SerialName("slug")
  val slug: String,

  @SerialName("language")
  val language: String? = null,

  @SerialName("games_count")
  val gamesCount: Int = 0,

  @SerialName("image_background")
  val imageBackground: String? = null
)
