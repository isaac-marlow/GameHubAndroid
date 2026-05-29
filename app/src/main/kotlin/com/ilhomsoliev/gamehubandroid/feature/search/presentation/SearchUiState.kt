package com.ilhomsoliev.gamehubandroid.feature.search.presentation

import com.ilhomsoliev.gamehubandroid.feature.game.domain.DeveloperModel
import com.ilhomsoliev.gamehubandroid.feature.game.domain.GameModel
import com.ilhomsoliev.gamehubandroid.feature.game.domain.GenreModel
import com.ilhomsoliev.gamehubandroid.feature.game.domain.PlatformModel
import com.ilhomsoliev.gamehubandroid.feature.game.domain.PublisherModel
import com.ilhomsoliev.gamehubandroid.feature.game.domain.StoreModel
import com.ilhomsoliev.gamehubandroid.feature.game.domain.TagModel
import com.ilhomsoliev.gamehubandroid.feature.search.data.GameSortOption

data class SearchUiState(
  val searchQuery: String = "",
  val isLoading: Boolean = false,
  val isLoadingNext: Boolean = false,
  val isLoadingTrendingNext: Boolean = false,
  val hasError: Boolean = false,
  val results: List<GameModel> = emptyList(),
  val quickTags: List<TagModel> = emptyList(),
  val quickGenres: List<GenreModel> = emptyList(),
  val platforms: List<PlatformModel> = emptyList(),
  val stores: List<StoreModel> = emptyList(),
  val developers: List<DeveloperModel> = emptyList(),
  val publishers: List<PublisherModel> = emptyList(),
  val sortOption: GameSortOption = GameSortOption.RELEVANCE,
  val releaseDatePeriod: ReleaseDatePeriod = ReleaseDatePeriod.ANY,
  val trendingGames: List<GameModel> = emptyList(),
  val genreId: Int? = null,
  val openedFromHomeSearch: Boolean = false,
  val savedGameIds: Set<Int> = emptySet(),
)