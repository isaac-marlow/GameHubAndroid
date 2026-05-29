package com.ilhomsoliev.gamehubandroid.feature.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ilhomsoliev.gamehubandroid.feature.auth.data.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SplashViewModel(
  val authRepository: AuthRepository,
) : ViewModel() {

  private val _uiState = MutableStateFlow<SplashUiState>(SplashUiState.Loading)
  val uiState = _uiState.asStateFlow()

  init {
    viewModelScope.launch(Dispatchers.IO) {
      val username = authRepository.getUsername()
      _uiState.value = SplashUiState.Loaded(username?.isNotEmpty() ?: false)
    }
  }
}