package com.ilhomsoliev.gamehubandroid.feature.details.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class RedditPostResponse(
  @SerialName("count") val count: Int,
  @SerialName("results") val posts: List<RedditPostDto>
)

@Serializable
data class RedditPostDto(
  @SerialName("id")
  val id: Int,

  @SerialName("name")
  val title: String, // The title of the post

  @SerialName("text")
  val contentHtml: String? = null, // The body (can be empty if it's just an image/link)

  @SerialName("image")
  val imageUrl: String? = null, // If the post has an image attached

  @SerialName("url")
  val externalUrl: String, // Link to the actual thread on Reddit.com

  @SerialName("username")
  val author: String,

  @SerialName("created")
  val createdDate: String // ISO Date string
)