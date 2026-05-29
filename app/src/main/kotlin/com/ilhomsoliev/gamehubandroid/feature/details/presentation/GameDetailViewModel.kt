package com.ilhomsoliev.gamehubandroid.feature.details.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ilhomsoliev.gamehubandroid.core.result.Result
import com.ilhomsoliev.gamehubandroid.feature.details.data.RedditRepository
import com.ilhomsoliev.gamehubandroid.feature.details.domain.RedditPostModel
import com.ilhomsoliev.gamehubandroid.feature.game.data.GameRepository
import com.ilhomsoliev.gamehubandroid.feature.game.domain.GameModel
import com.ilhomsoliev.gamehubandroid.feature.game.domain.ScreenshotModel
import com.ilhomsoliev.gamehubandroid.feature.search.data.GameSortOption
import com.ilhomsoliev.gamehubandroid.feature.search.data.SearchRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GameDetailViewModel(
  private val gameRepository: GameRepository,
  private val redditRepository: RedditRepository,
  private val searchRepository: SearchRepository,
) : ViewModel() {

  private val _uiState = MutableStateFlow<GameDetailUiState>(GameDetailUiState.Loading)
  val uiState = _uiState.asStateFlow()

  fun getGameById(id: Int) {
    viewModelScope.launch {
      val gameResult = gameRepository.getGameById(id)
      val screenshotsResult = gameRepository.getGameScreenshotById(id)
      val redditResult = redditRepository.getPosts(id)
      when {
        gameResult is Result.Error || screenshotsResult is Result.Error -> {
          _uiState.value = GameDetailUiState.Error
        }

        gameResult is Result.Success<GameModel> && screenshotsResult is Result.Success<List<ScreenshotModel>> -> {
          val redditPosts = (redditResult as? Result.Success<List<RedditPostModel>>)?.data.orEmpty()
          val game = gameResult.data
          val similarGames = game.genres.firstOrNull()?.id?.let { genreId ->
            when (val similarResult = searchRepository.searchGames(
              null,
              genreId,
              page = 1,
              sortOption = GameSortOption.RELEVANCE,
              releaseDateRange = null
            )) {
              is Result.Success -> similarResult.data.games
                .filterNot { it.id == game.id }
                .take(6)

              else -> emptyList()
            }
          }.orEmpty()
          _uiState.value = GameDetailUiState.Loaded(
            game,
            screenshotsResult.data,
            redditPosts,
            similarGames
          )
        }

        gameResult is Result.Loading || screenshotsResult is Result.Loading -> {
          _uiState.value = GameDetailUiState.Loading
        }
      }
    }
  }

  fun handleAction(action: GameDetailUiAction) {

  }

}