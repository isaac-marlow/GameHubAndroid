package com.ilhomsoliev.gamehubandroid.feature.details.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ilhomsoliev.gamehubandroid.core.ui.SpacerH
import com.ilhomsoliev.gamehubandroid.core.ui.SpacerV
import com.ilhomsoliev.gamehubandroid.core.ui.modifier.gameHubRoundedBackground
import com.ilhomsoliev.gamehubandroid.core.ui.theme.AppTheme

@Composable
fun LazyItemScope.AboutGame(
  modifier: Modifier = Modifier,
  description: String,
) {
  Column(
    modifier = modifier
      .fillMaxWidth()
      .padding(horizontal = 12.dp)
      .gameHubRoundedBackground()
      .padding(16.dp),
    horizontalAlignment = Alignment.Start,
    verticalArrangement = Arrangement.Center
  ) {
    Text(
      text = "About",
      style = AppTheme.typography.titleLarge.copy(fontSize = 18.sp),
      color = AppTheme.colors.onSurface
    )

    SpacerV(16.dp)

    val description = remember(description) {
      AnnotatedString.fromHtml(description)
    }
    val previewCharLimit = 200
    val shouldCollapse = description.length > previewCharLimit
    var isExpanded by rememberSaveable { mutableStateOf(false) }
    val collapsedDescription = remember(description) {
      if (shouldCollapse) {
        buildAnnotatedString {
          append(description.subSequence(0, previewCharLimit))
          append("...")
        }
      } else {
        description
      }
    }
    val showExpanded = isExpanded || !shouldCollapse

    Column(
      modifier = Modifier.animateContentSize()
    ) {
      Crossfade(
        targetState = showExpanded,
        label = "description"
      ) { expanded ->
        Text(
          text = if (expanded) description else collapsedDescription,
          style = AppTheme.typography.bodyMedium,
          color = AppTheme.colors.onSurface
        )
      }

      AnimatedVisibility(
        visible = shouldCollapse,
        enter = fadeIn(),
        exit = fadeOut()
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
            .wrapContentWidth(Alignment.End)
            .clickable { isExpanded = !isExpanded },
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = if (isExpanded) "Show less" else "Show more",
            style = AppTheme.typography.bodyMedium.copy(color = AppTheme.colors.primary)
          )
          SpacerH(4.dp)
          Icon(
            modifier = Modifier.size(18.dp),
            imageVector = if (isExpanded) {
              Icons.Filled.KeyboardArrowUp
            } else {
              Icons.Filled.KeyboardArrowDown
            },
            contentDescription = if (isExpanded) "collapse" else "expand",
            tint = AppTheme.colors.primary
          )
        }
      }
    }
  }
}