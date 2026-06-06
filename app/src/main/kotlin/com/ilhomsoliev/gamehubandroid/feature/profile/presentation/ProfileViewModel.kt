package com.ilhomsoliev.gamehubandroid.feature.profile.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ProfileViewModel() : ViewModel() {

  private val _uiState = MutableStateFlow(ProfileUiState())
  val uiState = _uiState.asStateFlow()

  fun handleAction(action: ProfileUiAction) {
    when (action) {
      ProfileUiAction.Logout -> TODO()
      ProfileUiAction.OpenEditProfile -> TODO()
      ProfileUiAction.OpenRecentlyViewed -> TODO()
      ProfileUiAction.OpenSavedPosts -> TODO()
      ProfileUiAction.OpenSettings -> TODO()
    }
  }
}