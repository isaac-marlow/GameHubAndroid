package com.ilhomsoliev.gamehubandroid.core.ui

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

@Composable
fun SpacerV(value: Dp) {
  Spacer(modifier = Modifier.height(value))
}

@Composable
fun SpacerH(value: Dp) {
  Spacer(modifier = Modifier.width(value))
}