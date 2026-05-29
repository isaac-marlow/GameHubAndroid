package com.ilhomsoliev.gamehubandroid.feature.library.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ilhomsoliev.gamehubandroid.feature.library.data.LibraryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LibraryViewModel(
  private val repository: LibraryRepository,
) : ViewModel() {

  private val _uiState = MutableStateFlow(LibraryUiState())
  val uiState = _uiState.asStateFlow()

  fun loadLibrary() {
    viewModelScope.launch(Dispatchers.IO) {
      _uiState.value = _uiState.value.copy(isLoading = true)
      val games = repository.getLibraryGames()
      _uiState.value = _uiState.value.copy(
        games = games,
        isLoading = false
      )
    }
  }

  fun removeGame(gameId: Int) {
    viewModelScope.launch(Dispatchers.IO) {
      repository.deleteGame(gameId)
      val games = repository.getLibraryGames()
      _uiState.value = _uiState.value.copy(games = games)
    }
  }
}
