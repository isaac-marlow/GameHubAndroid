package com.ilhomsoliev.gamehubandroid.feature.splash

sealed interface SplashUiState {

  object Loading : SplashUiState

  class Loaded(
    val authorized: Boolean
  ) : SplashUiState
}