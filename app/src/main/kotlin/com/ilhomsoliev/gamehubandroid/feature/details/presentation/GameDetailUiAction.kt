package com.ilhomsoliev.gamehubandroid.feature.details.presentation

sealed interface GameDetailUiAction {
  object OnBack : GameDetailUiAction

  object Share : GameDetailUiAction
}