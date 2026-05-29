package com.ilhomsoliev.gamehubandroid.feature.search.data.network.dto

import com.ilhomsoliev.gamehubandroid.feature.game.data.dto.GenreDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenreResponse(
  @SerialName("results") val genres: List<GenreDto>
)
