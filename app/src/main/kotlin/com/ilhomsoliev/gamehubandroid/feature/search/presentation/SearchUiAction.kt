package com.ilhomsoliev.gamehubandroid.feature.search.presentation

import com.ilhomsoliev.gamehubandroid.feature.search.data.GameSortOption

sealed interface SearchUiAction {

  class SearchValueChange(val value: String) : SearchUiAction
  class QuickTagClick(val tagName: String) : SearchUiAction
  class QuickGenreClick(val genreId: Int) : SearchUiAction
  class SortOptionClick(val sortOption: GameSortOption) : SearchUiAction
  class ReleaseDatePeriodClick(val period: ReleaseDatePeriod) : SearchUiAction
  data object LoadNextPage : SearchUiAction
  data object LoadNextTrending : SearchUiAction

}