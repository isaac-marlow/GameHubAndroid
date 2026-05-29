package com.ilhomsoliev.gamehubandroid.feature.splash

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.koin.androidx.compose.koinViewModel

@Composable
fun SplashScreen(
  viewModel: SplashViewModel = koinViewModel(),
  onIsAuthorizedLoaded: (Boolean) -> Unit
) {

  val uiState by viewModel.uiState.collectAsState()

  LaunchedEffect(uiState) {
    val uiState = uiState
    if (uiState is SplashUiState.Loaded) {
      onIsAuthorizedLoaded(uiState.authorized)
    }
  }

  Text("Splash screen here")

}