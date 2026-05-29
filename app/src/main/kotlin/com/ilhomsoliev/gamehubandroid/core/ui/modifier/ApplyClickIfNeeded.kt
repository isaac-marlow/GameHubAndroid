package com.ilhomsoliev.gamehubandroid.core.ui.modifier

import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun Modifier.applyClickIfNeeded(onClick: (() -> Unit)? = null): Modifier {
  return this.then(
    if (onClick == null) {
      Modifier
    } else {
      Modifier.clickable {
        onClick()
      }
    }
  )
}