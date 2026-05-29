package com.ilhomsoliev.gamehubandroid.feature.game.data

import com.ilhomsoliev.gamehubandroid.core.result.Result
import com.ilhomsoliev.gamehubandroid.feature.game.data.dto.GameResponse
import com.ilhomsoliev.gamehubandroid.feature.game.domain.GameModel
import com.ilhomsoliev.gamehubandroid.feature.game.domain.GenreModel
import com.ilhomsoliev.gamehubandroid.feature.game.domain.PagedGamesResult
import com.ilhomsoliev.gamehubandroid.feature.game.domain.PagedGenresResult
import com.ilhomsoliev.gamehubandroid.feature.game.domain.ScreenshotModel
import com.ilhomsoliev.gamehubandroid.feature.game.domain.toDomain

class GameRepository(
  private val gameApi: GameApi
) {

  suspend fun getGameOfTheDay(): Result<GameModel?> {
    val result: Result<GameResponse> = gameApi.getGameOfTheDay()
    return if (result.isSuccess) {
      Result.Success((result as Result.Success).data.games.firstOrNull()?.toDomain())
    } else {
      Result.Error((result as Result.Error).exception)
    }
  }

  suspend fun getNewReleases(page: Int): Result<PagedGamesResult> {
    val result: Result<GameResponse> = gameApi.getNewReleases(page)
    return if (result.isSuccess) {
      val data = (result as Result.Success).data
      Result.Success(
        PagedGamesResult(
          games = data.games.map { it.toDomain() },
          nextUrl = data.nextUrl
        )
      )
    } else {
      Result.Error((result as Result.Error).exception)
    }
  }

  suspend fun getTopRated(page: Int): Result<PagedGamesResult> {
    val result: Result<GameResponse> = gameApi.getTopRatedGames(page)
    return if (result.isSuccess) {
      val data = (result as Result.Success).data
      Result.Success(
        PagedGamesResult(
          games = data.games.map { it.toDomain() },
          nextUrl = data.nextUrl
        )
      )
    } else {
      Result.Error((result as Result.Error).exception)
    }
  }

  suspend fun getGameById(id: Int): Result<GameModel> {
    val result = gameApi.getGameById(id)
    return if (result.isSuccess) {
      Result.Success((result as Result.Success).data.toDomain())
    } else {
      Result.Error((result as Result.Error).exception)
    }
  }

  suspend fun getGameScreenshotById(id: Int): Result<List<ScreenshotModel>> {
    val result = gameApi.getGameScreenshots(id)
    return if (result.isSuccess) {
      Result.Success((result as Result.Success).data.results.map { it.toDomain() })
    } else {
      Result.Error((result as Result.Error).exception)
    }
  }

  suspend fun getGenres(page: Int): Result<PagedGenresResult> {
    val result = gameApi.getGenres(page)
    return if (result.isSuccess) {
      val data = (result as Result.Success).data
      Result.Success(
        PagedGenresResult(
          genres = data.results.map { it.toDomain() },
          nextUrl = data.nextUrl
        )
      )
    } else {
      Result.Error((result as Result.Error).exception)
    }
  }
}