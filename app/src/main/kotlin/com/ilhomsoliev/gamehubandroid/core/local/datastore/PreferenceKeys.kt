package com.ilhomsoliev.gamehubandroid.core.local.datastore

import androidx.datastore.preferences.core.stringPreferencesKey

object PreferenceKeys {

  object Auth {
    val NAME = stringPreferencesKey("name")
  }
}