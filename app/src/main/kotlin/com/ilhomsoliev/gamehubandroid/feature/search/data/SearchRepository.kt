package com.ilhomsoliev.gamehubandroid.feature.search.data

import com.ilhomsoliev.gamehubandroid.core.result.Result
import com.ilhomsoliev.gamehubandroid.feature.game.data.GameApi
import com.ilhomsoliev.gamehubandroid.feature.game.data.dto.GameResponse
import com.ilhomsoliev.gamehubandroid.feature.game.domain.toDomain

class SearchRepository(
  private val gameApi: GameApi
) {

  suspend fun searchGames(
    searchQuery: String?,
    genreId: Int?,
    sortOption: GameSortOption,
    releaseDateRange: String?,
    page: Int
  ): Result<SearchGamesResult> {
    val trimmedQuery = searchQuery?.trim().orEmpty()
    val query = GameSearchQuery(
      searchQuery = trimmedQuery.ifBlank { null },
      sorting = sortOption,
      page = page,
      dates = releaseDateRange,
      genres = genreId?.let { listOf(it.toString()) },
      excludeAdditions = true,
      excludeParents = true
    )

    val result: Result<GameResponse> = gameApi.searchGames(query)
    return if (result.isSuccess) {
      val data = (result as Result.Success).data
      Result.Success(
        SearchGamesResult(
          games = data.games.map { it.toDomain() },
          nextUrl = data.nextUrl
        )
      )
    } else {
      Result.Error((result as Result.Error).exception)
    }
  }
}