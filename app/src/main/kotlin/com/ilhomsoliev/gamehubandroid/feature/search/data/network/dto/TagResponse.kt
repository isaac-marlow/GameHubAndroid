package com.ilhomsoliev.gamehubandroid.feature.search.data.network.dto

import com.ilhomsoliev.gamehubandroid.feature.game.data.dto.TagDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TagResponse(
  @SerialName("results") val tags: List<TagDto>
)