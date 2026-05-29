package com.ilhomsoliev.gamehubandroid.feature.details.presentation

import com.ilhomsoliev.gamehubandroid.feature.details.domain.RedditPostModel
import com.ilhomsoliev.gamehubandroid.feature.game.domain.GameModel
import com.ilhomsoliev.gamehubandroid.feature.game.domain.ScreenshotModel

sealed interface GameDetailUiState {

  object Loading : GameDetailUiState

  object Error : GameDetailUiState

  class Loaded(
    val game: GameModel,
    val screenshots: List<ScreenshotModel>,
    val redditPosts: List<RedditPostModel>,
    val similarGames: List<GameModel>,
  ) : GameDetailUiState
}