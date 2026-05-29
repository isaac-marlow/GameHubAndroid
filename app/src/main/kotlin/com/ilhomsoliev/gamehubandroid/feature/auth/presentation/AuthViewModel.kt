package com.ilhomsoliev.gamehubandroid.feature.auth.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ilhomsoliev.gamehubandroid.feature.auth.data.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
  val authRepository: AuthRepository,
) : ViewModel() {
  private val _uiState = MutableStateFlow(AuthUiState())
  val uiState = _uiState.asStateFlow()

  fun handleAction(action: AuthUiAction, onSuccessAuth: () -> Unit) {
    when (action) {
      is AuthUiAction.EmailChange -> {
        _uiState.value = _uiState.value.copy(email = action.value)
      }

      is AuthUiAction.SignInClick -> {
        // TODO process data
        val email = _uiState.value.email
        if (email.isEmpty()) {
          return
        }
        // store email
        viewModelScope.launch(Dispatchers.IO) {
          authRepository.updateUsername(email)
        }
        onSuccessAuth()
      }
    }
  }
}