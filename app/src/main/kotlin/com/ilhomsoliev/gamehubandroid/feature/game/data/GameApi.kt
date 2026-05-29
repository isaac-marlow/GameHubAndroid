package com.ilhomsoliev.gamehubandroid.feature.game.data

import com.ilhomsoliev.gamehubandroid.core.network.safeApiCall
import com.ilhomsoliev.gamehubandroid.core.result.Result
import com.ilhomsoliev.gamehubandroid.feature.game.data.dto.GameDto
import com.ilhomsoliev.gamehubandroid.feature.game.data.dto.GameResponse
import com.ilhomsoliev.gamehubandroid.feature.game.data.dto.GenresResponse
import com.ilhomsoliev.gamehubandroid.feature.game.data.dto.ScreenshotsResponse
import com.ilhomsoliev.gamehubandroid.feature.search.data.GameSearchQuery
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

val baseUrl: String = "https://api.rawg.io/api"
val API_KEY = "221b1aa2ef5a45f9a9693b2d021af312"

class GameApi(
  private val client: HttpClient,
) {

  suspend fun getGameOfTheDay(): Result<GameResponse> = safeApiCall<GameResponse> {
    client.get("$baseUrl/games") {
      parameter("key", API_KEY)
      parameter("ordering", "-metacritic")
      parameter("dates", "2020-01-01,2025-12-31")
      parameter("page_size", 1)
    }
  }

  @OptIn(ExperimentalTime::class)
  suspend fun getNewReleases(page: Int): Result<GameResponse> {
    val timeZone = TimeZone.currentSystemDefault()
    val today = Clock.System.now().toLocalDateTime(timeZone).date

    val startWindow = today.minus(DatePeriod(months = 2))
    return safeApiCall<GameResponse> {
      client.get("$baseUrl/games") {
        parameter("key", API_KEY)
        parameter("ordering", "-released")
        parameter("dates", "$startWindow,$today")
        parameter("page", page)
        parameter("page_size", 10)
      }
    }
  }

  suspend fun getTopRatedGames(page: Int): Result<GameResponse> = withContext(Dispatchers.IO) {
    safeApiCall<GameResponse> {
      client.get("$baseUrl/games") {
        parameter("key", API_KEY)
        parameter("ordering", "-metacritic")
        parameter("exclude_additions", "true")
        parameter("page", page)
        parameter("page_size", 20)
      }
    }
  }


  suspend fun getGameById(id: Int): Result<GameDto> = withContext(Dispatchers.IO) {
    safeApiCall<GameDto> {
      client.get("$baseUrl/games/$id") {
        parameter("key", API_KEY)
      }
    }
  }

  suspend fun getGameScreenshots(gameId: Int): Result<ScreenshotsResponse> =
    withContext(Dispatchers.IO) {
      safeApiCall<ScreenshotsResponse> {
        client.get("$baseUrl/games/$gameId/screenshots") {
          parameter("key", API_KEY)
          parameter("page_size", 12)
        }
      }
    }

  suspend fun getGenres(page: Int): Result<GenresResponse> = safeApiCall {
    client.get("$baseUrl/genres") {
      parameter("key", API_KEY)
      parameter("ordering", "-games_count")
      parameter("page", page)
      parameter("page_size", 20)
    }
  }

  suspend fun searchGames(query: GameSearchQuery): Result<GameResponse> =
    withContext(Dispatchers.IO) {
      safeApiCall {
        client.get("$baseUrl/games") {
          parameter("key", API_KEY)

          query.searchQuery?.let { parameter("search", it) }
          if (query.searchPrecise) parameter("search_precise", true)
          if (query.searchExact) parameter("search_exact", true)

          parameter("page", query.page)
          parameter("page_size", query.pageSize)

          query.sorting?.let { parameter("ordering", it.value) }

          query.platforms?.let { parameter("platforms", it.joinToString(",")) }
          query.stores?.let { parameter("stores", it.joinToString(",")) }
          query.developers?.let { parameter("developers", it.joinToString(",")) }
          query.publishers?.let { parameter("publishers", it.joinToString(",")) }
          query.genres?.let { parameter("genres", it.joinToString(",")) }
          query.tags?.let { parameter("tags", it.joinToString(",")) }
          query.creators?.let { parameter("creators", it.joinToString(",")) }

          query.dates?.let { parameter("dates", it) }
          query.metacritic?.let { parameter("metacritic", it) }
          query.platformsCount?.let { parameter("platforms_count", it) }

          if (query.excludeAdditions) parameter("exclude_additions", true)
          if (query.excludeParents) parameter("exclude_parents", true)
          if (query.excludeGameSeries) parameter("exclude_game_series", true)
          query.excludeCollection?.let { parameter("exclude_collection", it) }
        }
      }
    }
}