package com.ilhomsoliev.gamehubandroid.feature.details.domain

data class RedditPostModel(
  val id: Int,
  val title: String,
  val contentHtml: String?,
  val imageUrl: String?,
  val externalUrl: String,
  val author: String,
  val createdDate: String
)
