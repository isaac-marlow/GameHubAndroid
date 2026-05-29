package com.ilhomsoliev.gamehubandroid.feature.auth.presentation

sealed interface AuthUiAction {

  data class EmailChange(val value: String) : AuthUiAction

  object SignInClick : AuthUiAction
}