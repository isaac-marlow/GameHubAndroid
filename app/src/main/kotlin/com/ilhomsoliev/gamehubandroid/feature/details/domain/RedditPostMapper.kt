package com.ilhomsoliev.gamehubandroid.feature.details.domain

import com.ilhomsoliev.gamehubandroid.feature.details.data.RedditPostDto
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

fun RedditPostDto.toDomain(): RedditPostModel {
  return RedditPostModel(
    id = id,
    title = title,
    contentHtml = contentHtml,
    imageUrl = imageUrl,
    externalUrl = externalUrl,
    author = author,
    createdDate = formatRedditCreatedDate(createdDate)
  )
}

private fun formatRedditCreatedDate(value: String): String {
  val outputFormatter = DateTimeFormatter.ofPattern("MMM d, yyyy", Locale.ENGLISH)
  return try {
    OffsetDateTime.parse(value).format(outputFormatter)
  } catch (error: Exception) {
    value
  }
}
