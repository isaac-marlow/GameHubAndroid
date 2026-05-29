package com.ilhomsoliev.gamehubandroid.feature.auth.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ilhomsoliev.gamehubandroid.core.ui.SpacerV
import com.ilhomsoliev.gamehubandroid.core.ui.theme.AppTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun AuthScreen(
  viewModel: AuthViewModel = koinViewModel(),
  onSuccessAuth: () -> Unit,
) {
  val state by viewModel.uiState.collectAsState()

  AuthContent(state) {
    viewModel.handleAction(it, onSuccessAuth)
  }
}

@Composable
private fun AuthContent(
  state: AuthUiState,
  handleAction: (AuthUiAction) -> Unit
) {
  Scaffold(
    topBar = {

    }
  ) { innerPadding ->
    LazyColumn(
      modifier = Modifier
        .fillMaxWidth()
        .padding(innerPadding)
    ) {
      item {
        Column(
          modifier = Modifier.fillMaxWidth(),
          verticalArrangement = Arrangement.Center,
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          SpacerV(16.dp)
          // TODO place icon

          Text(
            text = "GameHub",
            style = AppTheme.typography.title,
            color = AppTheme.colors.primary
          )
          SpacerV(16.dp)
          Text(
            "Your ultimate gaming companion",
            style = AppTheme.typography.body,
            color = AppTheme.colors.secondaryText
          )
        }
      }

      item { // TODO make a pager
      }

      item { // TODO textfield email
        TextField(state.email, onValueChange = {
          handleAction(AuthUiAction.EmailChange(it))
        })
      }

      item { // TODO textfield password
      }

      item { // TODO forgot password
      }

      item { // TODO button sign in/up
        Button({ handleAction(AuthUiAction.SignInClick) }) {
          Text("Sign in")
        }
      }

      item { // TODO or continue with text
      }

      item { // TODO continue with google/github
      }

      item { // TODO terms of service and privacy policy
      }
    }
  }
}