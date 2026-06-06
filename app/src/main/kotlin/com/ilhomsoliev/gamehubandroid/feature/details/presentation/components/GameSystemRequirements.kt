package com.ilhomsoliev.gamehubandroid.feature.details.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ilhomsoliev.gamehubandroid.core.ui.SpacerV
import com.ilhomsoliev.gamehubandroid.core.ui.modifier.gameHubRoundedBackground
import com.ilhomsoliev.gamehubandroid.core.ui.theme.AppTheme
import com.ilhomsoliev.gamehubandroid.feature.game.domain.RequirementsModel

@Composable
fun LazyItemScope.GameSystemRequirements(
  modifier: Modifier = Modifier,
  requirements: RequirementsModel?
) {
  requirements ?: return
  var isExpanded by rememberSaveable { mutableStateOf(false) }

  Column(
    modifier = modifier
      .fillMaxWidth()
      .padding(horizontal = 12.dp)
      .gameHubRoundedBackground()
      .padding(16.dp),
    horizontalAlignment = Alignment.Start,
    verticalArrangement = Arrangement.Center
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .clickable { isExpanded = !isExpanded },
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween,
    ) {
      Text(
        text = "System Requirements",
        style = AppTheme.typography.titleLarge.copy(fontSize = 18.sp),
        color = AppTheme.colors.onSurface
      )

      Icon(
        imageVector = if (isExpanded) {
          Icons.Filled.KeyboardArrowUp
        } else {
          Icons.Filled.KeyboardArrowDown
        },
        contentDescription = if (isExpanded) "collapse" else "expand",
        tint = AppTheme.colors.onSurface
      )

    }

    AnimatedVisibility(visible = isExpanded) {
      Column(modifier = Modifier.animateContentSize()) {
        SpacerV(24.dp)

        Text(
          text = "Minimum",
          style = AppTheme.typography.titleLarge,
          color = AppTheme.colors.primary
        )
        SpacerV(12.dp)

        Text(
          text = requirements.minimum,
          style = AppTheme.typography.bodyMedium,
          color = AppTheme.colors.onSurface
        )
        SpacerV(24.dp)

        Text(
          text = "Recommended",
          style = AppTheme.typography.titleLarge,
          color = AppTheme.colors.primary
        )

        SpacerV(12.dp)

        Text(
          text = requirements.recommended,
          style = AppTheme.typography.bodyMedium,
          color = AppTheme.colors.onSurface
        )
      }
    }
  }
}