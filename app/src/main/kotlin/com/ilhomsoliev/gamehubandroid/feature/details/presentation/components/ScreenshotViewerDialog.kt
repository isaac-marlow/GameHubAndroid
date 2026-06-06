package com.ilhomsoliev.gamehubandroid.feature.details.presentation.components

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.calculatePan
import androidx.compose.foundation.gestures.calculateZoom
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.ilhomsoliev.gamehubandroid.core.ui.GlideAsyncImage
import com.ilhomsoliev.gamehubandroid.core.ui.theme.AppTheme
import com.ilhomsoliev.gamehubandroid.feature.game.domain.ScreenshotModel
import kotlinx.coroutines.launch

@Composable
fun ScreenshotViewerDialog(
  screenshots: List<ScreenshotModel>,
  initialIndex: Int,
  onDismiss: () -> Unit
) {
  if (screenshots.isEmpty()) {
    return
  }

  Dialog(
    onDismissRequest = onDismiss,
    properties = DialogProperties(usePlatformDefaultWidth = false)
  ) {
    BackHandler(onBack = onDismiss)

    val safeIndex = initialIndex.coerceIn(0, screenshots.lastIndex)
    val pagerState = rememberPagerState(
      initialPage = safeIndex,
      pageCount = { screenshots.size }
    )
    var isZoomed by remember { mutableStateOf(false) }

    LaunchedEffect(pagerState.currentPage) {
      isZoomed = false
    }

    Box(
      modifier = Modifier
        .fillMaxSize()
        .background(Color.Black)
    ) {
      HorizontalPager(
        state = pagerState,
        modifier = Modifier.fillMaxSize(),
        userScrollEnabled = !isZoomed
      ) { page ->
        val screenshot = screenshots[page]
        ZoomableImage(
          imageUrl = screenshot.image,
          modifier = Modifier.fillMaxSize(),
          onZoomChanged = { zoomed ->
            if (pagerState.currentPage == page) {
              isZoomed = zoomed
            }
          }
        )
      }

      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        IconButton(onClick = onDismiss) {
          Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "Close",
            tint = Color.White
          )
        }
        Text(
          text = "${pagerState.currentPage + 1} / ${screenshots.size}",
          style = AppTheme.typography.bodyMedium,
          color = Color.White
        )
      }
    }
  }
}

@Composable
private fun ZoomableImage(
  imageUrl: String?,
  modifier: Modifier = Modifier,
  onZoomChanged: (Boolean) -> Unit = {}
) {
  val scale = remember { Animatable(1f) }
  val offsetX = remember { Animatable(0f) }
  val offsetY = remember { Animatable(0f) }
  var containerSize by remember { mutableStateOf(IntSize.Zero) }
  val scope = rememberCoroutineScope()

  LaunchedEffect(scale.value) {
    onZoomChanged(scale.value > 1f)
  }

  val resetZoom = suspend {
    scale.animateTo(1f, animationSpec = tween(durationMillis = 220))
    offsetX.animateTo(0f, animationSpec = tween(durationMillis = 220))
    offsetY.animateTo(0f, animationSpec = tween(durationMillis = 220))
  }

  val maxOffsetX = ((scale.value - 1f) * containerSize.width / 2f).coerceAtLeast(0f)
  val maxOffsetY = ((scale.value - 1f) * containerSize.height / 2f).coerceAtLeast(0f)

  BoxWithConstraints(
    modifier = modifier
      .clipToBounds()
      .pointerInput(Unit) {
        detectTapGestures(
          onDoubleTap = {
            scope.launch {
              if (scale.value <= 1f) {
                scale.animateTo(2f, animationSpec = tween(durationMillis = 220))
              } else {
                resetZoom()
              }
            }
          }
        )
      }
      .pointerInput(containerSize) {
        awaitEachGesture {
          awaitFirstDown(requireUnconsumed = false)
          var scaleValue = scale.value
          var offsetXValue = offsetX.value
          var offsetYValue = offsetY.value
          do {
            val event = awaitPointerEvent()
            val zoomChange = event.calculateZoom()
            val panChange = event.calculatePan()
            val isPinch = event.changes.size > 1 && zoomChange != 1f
            val isPan = scaleValue > 1f && panChange != Offset.Zero
            if (isPinch || isPan) {
              event.changes.forEach { it.consume() }
              val newScale = (scaleValue * zoomChange).coerceIn(1f, 5f)
              val maxX = ((newScale - 1f) * containerSize.width / 2f).coerceAtLeast(0f)
              val maxY = ((newScale - 1f) * containerSize.height / 2f).coerceAtLeast(0f)
              val newOffsetX =
                if (newScale == 1f) 0f else (offsetXValue + panChange.x).coerceIn(-maxX, maxX)
              val newOffsetY =
                if (newScale == 1f) 0f else (offsetYValue + panChange.y).coerceIn(-maxY, maxY)
              scaleValue = newScale
              offsetXValue = newOffsetX
              offsetYValue = newOffsetY
              scope.launch {
                scale.snapTo(newScale)
                offsetX.snapTo(newOffsetX)
                offsetY.snapTo(newOffsetY)
              }
            }
          } while (event.changes.any { it.pressed })
        }
      },
    contentAlignment = Alignment.Center
  ) {
    if (containerSize == IntSize.Zero) {
      containerSize = IntSize(constraints.maxWidth, constraints.maxHeight)
    }
    GlideAsyncImage(
      imageUrl = imageUrl,
      contentDescription = "screenshot",
      contentScale = ContentScale.Fit,
      modifier = Modifier
        .fillMaxSize()
        .graphicsLayer(
          scaleX = scale.value,
          scaleY = scale.value,
          translationX = offsetX.value.coerceIn(-maxOffsetX, maxOffsetX),
          translationY = offsetY.value.coerceIn(-maxOffsetY, maxOffsetY)
        )
    )
  }
}
