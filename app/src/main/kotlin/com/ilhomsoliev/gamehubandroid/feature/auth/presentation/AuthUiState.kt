package com.ilhomsoliev.gamehubandroid.feature.auth.presentation

data class AuthUiState(
  val email: String = "",
  val password: String = "",
  val isLoading: Boolean = false,
)