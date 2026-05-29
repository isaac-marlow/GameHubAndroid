package com.ilhomsoliev.gamehubandroid.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import com.ilhomsoliev.gamehubandroid.core.navigation.NavigationApp
import com.ilhomsoliev.gamehubandroid.core.ui.theme.AppThemeRoot

class MainActivity : ComponentActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      val isDark = isSystemInDarkTheme()
      AppThemeRoot(isDark = isDark) {
        NavigationApp(modifier = Modifier.padding())
      }
    }
  }
}
