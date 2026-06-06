package com.ilhomsoliev.gamehubandroid.core.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ilhomsoliev.gamehubandroid.core.ui.theme.AppTheme

@Composable
fun InformationBlock(
  modifier: Modifier = Modifier,
  @DrawableRes imageId: Int,
  title: String,
  subtitle: String,
  buttonIcon: ImageVector,
  buttonText: String,
  onButtonClick: () -> Unit
) {
  Column(
    modifier = modifier,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Image(
      painter = painterResource(id = imageId),
      contentDescription = null,
    )
    Text(
      text = title,
      style = AppTheme.typography.headlineSmall,
      color = AppTheme.colors.onSurface
    )
    SpacerV(8.dp)

    Text(
      modifier = Modifier.padding(horizontal = 80.dp),
      text = subtitle,
      style = AppTheme.typography.bodyLarge,
      textAlign = androidx.compose.ui.text.style.TextAlign.Center,
      color = AppTheme.colors.onSurface
    )
    SpacerV(16.dp)

    GHButton(icon = buttonIcon, text = buttonText, onClick = onButtonClick)
  }
}