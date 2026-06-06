package com.ilhomsoliev.gamehubandroid.feature.profile.presentation

sealed class ProfileUiAction {
  object OpenSettings : ProfileUiAction()
  object OpenEditProfile : ProfileUiAction()
  object OpenSavedPosts : ProfileUiAction()
  object OpenRecentlyViewed : ProfileUiAction()
  object Logout : ProfileUiAction()
}