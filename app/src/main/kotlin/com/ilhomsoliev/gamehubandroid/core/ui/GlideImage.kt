package com.ilhomsoliev.gamehubandroid.core.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import com.skydoves.landscapist.glide.GlideImageState

/**
 * Composable function for loading and displaying images using Glide via Landscapist.
 *
 * @param imageUrl The URL or path of the image to load
 * @param contentDescription Description for accessibility
 * @param modifier Modifier for styling
 * @param contentScale How the image should be scaled within its bounds
 * @param size Optional size for the image (defaults to fillMaxSize)
 */
@Composable
fun GlideAsyncImage(
  imageUrl: String?,
  contentDescription: String?,
  modifier: Modifier = Modifier,
  contentScale: ContentScale = ContentScale.Fit,
  size: Dp? = null
) {
  val finalModifier = if (size != null) {
    modifier.size(size)
  } else {
    modifier.fillMaxSize()
  }

  GlideImage(
    imageModel = { imageUrl },
    imageOptions = ImageOptions(
      contentScale = contentScale,
      alignment = Alignment.Center,
      contentDescription = contentDescription
    ),
    modifier = finalModifier,
    loading = {
      Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
      }
    },
    failure = {
      Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Failed to load image")
      }
    }
  )
}

/**
 * Composable function for loading and displaying images using Glide with custom loading and error states.
 */
@Composable
fun GlideAsyncImageWithStates(
  imageUrl: String?,
  contentDescription: String?,
  modifier: Modifier = Modifier,
  contentScale: ContentScale = ContentScale.Fit,
  size: Dp? = null,
  loading: @Composable (BoxScope.(imageState: GlideImageState.Loading) -> Unit)? = {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
      CircularProgressIndicator()
    }
  },
  failure: @Composable (BoxScope.(imageState: GlideImageState.Failure) -> Unit)? = {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
      Text("Failed to load image")
    }
  }
) {
  val finalModifier = if (size != null) {
    modifier.size(size)
  } else {
    modifier.fillMaxSize()
  }

  GlideImage(
    imageModel = { imageUrl },
    imageOptions = ImageOptions(
      contentScale = contentScale,
      alignment = Alignment.Center,
      contentDescription = contentDescription
    ),
    modifier = finalModifier,
    loading = loading,
    failure = failure
  )
}
