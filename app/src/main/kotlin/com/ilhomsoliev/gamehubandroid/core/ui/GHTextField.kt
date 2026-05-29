package com.ilhomsoliev.gamehubandroid.core.ui

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ilhomsoliev.gamehubandroid.core.ui.theme.AppTheme
import com.ilhomsoliev.gamehubandroid.core.ui.theme.AppThemeRoot

@Composable
fun GHTextField(
  modifier: Modifier = Modifier,
  textFieldModifier: Modifier = Modifier,
  value: String,
  icon: ImageVector,
  placeholder: String = "",
  keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
  onClick: (() -> Unit)? = null,
  onValueChange: (String) -> Unit,
) {
  Box(modifier = modifier) { // 1. Remove clickable from here
    OutlinedTextField(
      value = value,
      onValueChange = onValueChange,
      modifier = textFieldModifier.fillMaxWidth(),
      shape = CircleShape,
      readOnly = onClick != null, // Keeps keyboard from opening
      leadingIcon = {
        Icon(
          imageVector = icon,
          contentDescription = "icon",
          tint = AppTheme.colors.secondaryText
        )
      },
      placeholder = {
        Text(
          text = placeholder,
          color = AppTheme.colors.secondaryText
        )
      },
      trailingIcon = {
        AnimatedVisibility(
          visible = value.isNotEmpty(),
          enter = fadeIn(),
          exit = fadeOut()
        ) {
          IconButton(onClick = { onValueChange("") }) {
            Icon(
              imageVector = Icons.Default.Close,
              contentDescription = "clear",
              tint = AppTheme.colors.secondaryText
            )
          }
        }
      },
      singleLine = true,
      keyboardOptions = keyboardOptions,
      colors = OutlinedTextFieldDefaults.colors(
        focusedContainerColor = AppTheme.colors.secondary,
        unfocusedContainerColor = AppTheme.colors.secondary,
        disabledContainerColor = AppTheme.colors.secondary,
        focusedTextColor = AppTheme.colors.text,
        unfocusedTextColor = AppTheme.colors.text,
        cursorColor = AppTheme.colors.secondaryText,
        focusedBorderColor = AppTheme.colors.secondaryText,
        unfocusedBorderColor = AppTheme.colors.secondaryText,
      )
    )

    // 2. Add the Overlay Box here
    if (onClick != null) {
      Box(
        modifier = Modifier
          .matchParentSize() // Fills the size of the parent Box
          // 3. Add padding to the right so the 'Close' button is still clickable
          .padding(end = if(value.isNotEmpty()) 48.dp else 0.dp)
          .clickable {
            Log.d("Hello", "click called")
            onClick()
          }
      )
    }
  }
}

@Preview()
@Composable
fun EmailTextFieldPreview() {
  AppThemeRoot(true) {
    Box(
      modifier = Modifier
        .fillMaxSize()
        .background(AppTheme.colors.background)
    ) {
      GHTextField(
        value = "",
        onValueChange = {},
        modifier = Modifier
          .fillMaxWidth()
          .padding(16.dp),
        icon = Icons.Default.Email,
        placeholder = "Some placeholder"
      )
    }
  }
}
