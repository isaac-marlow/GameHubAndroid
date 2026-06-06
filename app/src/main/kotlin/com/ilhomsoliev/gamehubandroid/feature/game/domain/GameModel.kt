package com.ilhomsoliev.gamehubandroid.feature.game.domain

data class GameModel(
  val id: Int,
  val slug: String,
  val name: String,
  val releasedDate: String?,
  val releaseYear: String?,
  val isTba: Boolean,
  val backgroundImage: String?,
  val rating: String,
  val platforms: List<PlatformModel>,
  val stores: List<StoreModel>,
  val genres: List<GenreModel>,
  val tags: List<TagModel>,
  val esrbRating: EsrbRatingModel?,
  val description: String?,
  val redditDescription: String?
) {

  fun hasAnyPlatform(): Boolean {
    return platforms.isNotEmpty()
  }

  fun isAvailableOnPc(): Boolean {
    return platforms.any { it.slug == "pc" }
  }

  fun isAvailableOnConsole(): Boolean {
    return platforms.any { it.slug == "playstation5" } // TODO add more
  }

  companion object {
    val sample = GameModel(
      id = 1,
      slug = "sample-slug",
      name = "Sample Game",
      releasedDate = "2023-01-01",
      releaseYear = "2023",
      isTba = false,
      backgroundImage = "https://avatars.githubusercontent.com/u/97165289",
      rating = "4.3",
      platforms = emptyList(),
      stores = emptyList(),
      genres = emptyList(),
      tags = emptyList(),
      esrbRating = null,
      description = "",
      redditDescription = "",
    )
  }
}

class RequirementsModel(
  val minimum: String,
  val recommended: String,
)

data class StoreModel(
  val id: Int,
  val name: String,
  val slug: String
)

data class DeveloperModel(
  val id: Int,
  val name: String,
  val slug: String,
  val gamesCount: Int,
  val imageBackground: String?
)

data class PublisherModel(
  val id: Int,
  val name: String,
  val slug: String,
  val gamesCount: Int,
  val imageBackground: String?
)
