package com.ilhomsoliev.gamehubandroid.feature.game.domain

import com.ilhomsoliev.gamehubandroid.feature.game.data.dto.EsrbRatingDto
import com.ilhomsoliev.gamehubandroid.feature.game.data.dto.GameDto
import com.ilhomsoliev.gamehubandroid.feature.game.data.dto.GenreDto
import com.ilhomsoliev.gamehubandroid.feature.game.data.dto.ScreenshotDto
import com.ilhomsoliev.gamehubandroid.feature.game.data.dto.StoreWrapperDto
import com.ilhomsoliev.gamehubandroid.feature.game.data.dto.TagDto
import com.ilhomsoliev.gamehubandroid.feature.game.data.dto.platform.PlatformWrapperDto
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

fun ScreenshotDto.toDomain(): ScreenshotModel {
  return ScreenshotModel(
    id = id,
    image = image,
    width = width,
    height = height,
    isDeleted = isDeleted
  )
}

fun GameDto.toDomain(): GameModel {
  return GameModel(
    id = id,
    slug = slug,
    name = name,
    releasedDate = formatReleasedDate(releasedDate),
    releaseYear = formatReleaseYear(releasedDate),
    isTba = isTba,
    backgroundImage = backgroundImage,
    rating = rating.toString(),
    platforms = platforms.orEmpty().map { it.toDomain() },
    stores = stores.orEmpty().map { it.toDomain() },
    genres = genres.orEmpty().map { it.toDomain() },
    tags = tags.orEmpty().map { it.toDomain() },
    esrbRating = esrbRating?.toDomain(),
    description = description,
    redditDescription = redditDescription,
  )
}

private fun PlatformWrapperDto.toDomain(): PlatformModel {
  val platform = this.platform ?: return PlatformModel(
    id = -1,
    name = "",
    slug = "",
    releasedAt = null,
    requirements = null,
  )
  val releasedAt = formatReleasedDate(this.releasedAt)
  return PlatformModel(
    id = platform.id,
    name = platform.name,
    slug = platform.slug,
    releasedAt = releasedAt,
    requirements = if (
      requirements != null &&
      requirements.minimum != null &&
      requirements.recommended != null
    ) {
      RequirementsModel(
        requirements.minimum,
        requirements.recommended
      )
    } else {
      null
    },
  )
}

private fun StoreWrapperDto.toDomain(): StoreModel {
  return StoreModel(
    id = store.id,
    name = store.name,
    slug = store.slug
  )
}

fun GenreDto.toDomain(): GenreModel {
  return GenreModel(
    id = id,
    name = name,
    slug = slug,
    imageBackground = imageBackground,
    gamesCount = gamesCount,
  )
}

private fun TagDto.toDomain(): TagModel {
  return TagModel(
    id = id,
    name = name,
    slug = slug,
    language = language,
    gamesCount = gamesCount,
    imageBackground = imageBackground
  )
}

private fun EsrbRatingDto.toDomain(): EsrbRatingModel {
  return EsrbRatingModel(
    id = id,
    name = name,
    slug = slug
  )
}

private fun formatReleasedDate(value: String?): String? {
  if (value.isNullOrBlank()) return value
  val inputFormatter = DateTimeFormatter.ISO_LOCAL_DATE
  val outputFormatter = DateTimeFormatter.ofPattern("MMMM d, yyyy", Locale.ENGLISH)
  return try {
    LocalDate.parse(value, inputFormatter).format(outputFormatter)
  } catch (error: Exception) {
    value
  }
}

private fun formatReleaseYear(value: String?): String? {
  if (value.isNullOrBlank()) return null
  val inputFormatter = DateTimeFormatter.ISO_LOCAL_DATE
  return try {
    LocalDate.parse(value, inputFormatter).year.toString()
  } catch (error: Exception) {
    Regex("\\b\\d{4}\\b").find(value)?.value
  }
}
