package com.ilhomsoliev.gamehubandroid.feature.profile.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ilhomsoliev.gamehubandroid.core.ui.SpacerV
import com.ilhomsoliev.gamehubandroid.core.ui.icons.SearchActivityIcon
import com.ilhomsoliev.gamehubandroid.core.ui.modifier.applyCardBackground
import com.ilhomsoliev.gamehubandroid.core.ui.screen_skeleton.GHTopAppBar
import com.ilhomsoliev.gamehubandroid.core.ui.slot.GHSlot
import com.ilhomsoliev.gamehubandroid.core.ui.theme.AppTheme
import com.ilhomsoliev.gamehubandroid.feature.profile.presentation.components.ProfileLibraryStatisticsBlock
import com.ilhomsoliev.gamehubandroid.feature.profile.presentation.components.UserInfoBlock
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProfileScreen(
  viewModel: ProfileViewModel = koinViewModel(),
  openSettings: () -> Unit
) {

  val state by viewModel.uiState.collectAsState()

  ProfileContent(
    state = state,
    onAction = {
      when (it) {
        ProfileUiAction.Logout -> TODO()
        ProfileUiAction.OpenEditProfile -> TODO()
        ProfileUiAction.OpenRecentlyViewed -> TODO()
        ProfileUiAction.OpenSavedPosts -> TODO()
        ProfileUiAction.OpenSettings -> openSettings()
      }
      viewModel.handleAction(it)
    }
  )
}

@Composable
fun ProfileContent(
  state: ProfileUiState,
  onAction: (ProfileUiAction) -> Unit
) {
  Scaffold(
    containerColor = AppTheme.colors.surface,
    topBar = {
      GHTopAppBar(
        title = "Profile",
        actions = {
          IconButton(onClick = {
            onAction(ProfileUiAction.OpenSettings)
          }) {
            Icon(imageVector = Icons.Default.Settings, contentDescription = "")
          }
        })
    }
  ) { paddingValues ->
    LazyColumn(
      modifier = Modifier
        .fillMaxWidth()
        .padding(
          top = paddingValues.calculateTopPadding(),
          bottom = paddingValues.calculateBottomPadding(),
          start = 16.dp,
          end = 16.dp
        )
    ) {
      item {
        UserInfoBlock(
          modifier = Modifier
            .fillMaxWidth(),
          avatarUrl = "",
          name = state.name,
          bio = state.bio,
          onEditClick = {
            onAction(ProfileUiAction.OpenEditProfile)
          }
        )
        SpacerV(24.dp)
      }

      ProfileLibraryStatisticsBlock(
        completed = 10,
        currentlyPlaying = 10,
        favorites = 10,
        wishlist = 10,
      )

      item {
        SpacerV(24.dp)

        Column(
          modifier = Modifier
            .fillMaxWidth()
            .applyCardBackground()
        ) {
          GHSlot(
            modifier = Modifier.fillMaxWidth(),
            icon = Icons.Default.Bookmark,
            text = "Saved posts",
            actionIcon = Icons.AutoMirrored.Filled.ArrowForwardIos,
            hasDivider = true,
            onClick = {
              onAction(ProfileUiAction.OpenSavedPosts)
            }
          )
          GHSlot(
            modifier = Modifier.fillMaxWidth(),
            icon = SearchActivityIcon,
            text = "Recently Viewed",
            actionIcon = Icons.AutoMirrored.Filled.ArrowForwardIos,
            hasDivider = false,
            onClick = {
              onAction(ProfileUiAction.OpenSavedPosts)
            }
          )
        }
      }
    }
  }
}
