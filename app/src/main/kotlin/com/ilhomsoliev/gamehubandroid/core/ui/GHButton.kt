package com.ilhomsoliev.gamehubandroid.core.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.ilhomsoliev.gamehubandroid.core.ui.theme.AppTheme

@Composable
fun GHButton(
  modifier: Modifier = Modifier,
  icon: ImageVector,
  text: String,
  onClick: () -> Unit
) {
  Row(
    modifier = modifier
      .clip(CircleShape)
      .background(AppTheme.colors.primary)
      .clickable(onClick = onClick)
      .padding(8.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    Icon(imageVector = icon, contentDescription = "")
    SpacerH(8.dp)
    Text(
      text = text, style = AppTheme.typography.bodyLarge.copy(color = AppTheme.colors.onPrimary),
    )
  }
}