package com.ilhomsoliev.gamehubandroid.feature.profile.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ilhomsoliev.gamehubandroid.core.ui.GHButton
import com.ilhomsoliev.gamehubandroid.core.ui.SpacerV
import com.ilhomsoliev.gamehubandroid.core.ui.applyCardBackground
import com.ilhomsoliev.gamehubandroid.core.ui.theme.AppTheme

@Composable
fun UserInfoBlock(
  modifier: Modifier = Modifier,
  avatarUrl: String,
  name: String,
  bio: String,
  onEditClick: () -> Unit,
) {
  Column(
    modifier = modifier
      .fillMaxWidth()
      .applyCardBackground(),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    SpacerV(16.dp)
    Box(
      modifier = Modifier
        .size(72.dp)
        .clip(CircleShape)
        .background(Color.Black)
    )
    SpacerV(18.dp)
    Text(text = name, style = AppTheme.typography.headlineSmall, color = AppTheme.colors.onSurface)
    SpacerV(8.dp)
    Text(
      text = bio,
      style = AppTheme.typography.bodyMedium,
      color = AppTheme.colors.onSurfaceVar,
      textAlign = TextAlign.Center
    )
    SpacerV(8.dp)
    GHButton(icon = Icons.Default.Edit, text = "Edit Profile", onClick = onEditClick)
    SpacerV(18.dp)
  }
}