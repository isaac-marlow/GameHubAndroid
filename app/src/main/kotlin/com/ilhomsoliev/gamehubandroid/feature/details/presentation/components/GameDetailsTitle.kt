package com.ilhomsoliev.gamehubandroid.feature.details.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.VideogameAsset
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ilhomsoliev.gamehubandroid.core.ui.GlideAsyncImage
import com.ilhomsoliev.gamehubandroid.core.ui.SpacerH
import com.ilhomsoliev.gamehubandroid.core.ui.modifier.gameHubRoundedBackground
import com.ilhomsoliev.gamehubandroid.core.ui.theme.AppTheme

@Composable
fun LazyItemScope.GameDetailsTitle(
  modifier: Modifier = Modifier,
  image: String?,
  title: String,
  releaseDate: String?,
  isAvailableOnPc: Boolean,
  isAvailableOnConsole: Boolean,
  onBack: () -> Unit,
  onShare: () -> Unit
) {
  val config = LocalConfiguration.current
  val screenHeightDp = config.screenHeightDp
  val screenWidthDp = config.screenWidthDp

  Column(modifier = modifier.fillMaxWidth()) {
    Box(
      modifier = modifier
        .fillMaxWidth()
        .height((screenHeightDp / 2).dp),
      contentAlignment = Alignment.TopCenter
    ) {
      GlideAsyncImage(
        imageUrl = image,
        contentDescription = "background image",
        modifier = Modifier
          .size(screenWidthDp.dp),
        contentScale = ContentScale.Crop
      )
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        IconButton(
          modifier = Modifier,
          onClick = onBack
        ) {
          Icon(
            modifier = Modifier,
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "back"
          )
        }

        IconButton(
          modifier = Modifier,
          onClick = onShare
        ) {
          Icon(
            modifier = Modifier,
            imageVector = Icons.Filled.Share,
            contentDescription = "share"
          )
        }
      }
    }

    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 12.dp)
        .gameHubRoundedBackground()
        .padding(16.dp),
      horizontalAlignment = Alignment.Start,
      verticalArrangement = Arrangement.Center
    ) {
      Text(
        text = title,
        style = AppTheme.typography.title.copy(fontSize = 32.sp),
        color = AppTheme.colors.text
      )
      releaseDate?.let { releasedDate ->
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.Center
        ) {
          Icon(
            modifier = Modifier.size(16.dp),
            imageVector = Icons.Default.CalendarToday,
            contentDescription = "calendar",
            tint = AppTheme.colors.secondaryText
          )

          SpacerH(8.dp)

          Text(
            text = releasedDate,
            style = AppTheme.typography.body,
            color = AppTheme.colors.secondaryText
          )

          if (isAvailableOnPc) {
            SpacerH(8.dp)

            Icon(
              modifier = Modifier.size(16.dp),
              imageVector = Icons.Default.Computer,
              contentDescription = "computer",
              tint = AppTheme.colors.secondaryText
            )
          }

          if (isAvailableOnConsole) {
            SpacerH(8.dp)

            Icon(
              modifier = Modifier.size(16.dp),
              imageVector = Icons.Default.VideogameAsset,
              contentDescription = "console",
              tint = AppTheme.colors.secondaryText
            )
          }
        }
      }

      Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
      ) {
        // TODO critic score, user rating, avg playtime
      }
    }
  }
}