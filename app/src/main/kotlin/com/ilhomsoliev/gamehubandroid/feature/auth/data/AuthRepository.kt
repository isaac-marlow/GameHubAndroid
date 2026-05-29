package com.ilhomsoliev.gamehubandroid.feature.auth.data

import com.ilhomsoliev.gamehubandroid.core.local.datastore.PreferenceKeys
import com.ilhomsoliev.gamehubandroid.core.local.datastore.PreferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class AuthRepository(
  private val preferencesDataStore: PreferencesDataStore
) {

  suspend fun getUsername(): String? = getUsernameAsFlow().first()

  fun getUsernameAsFlow(): Flow<String?> = preferencesDataStore.getString(PreferenceKeys.Auth.NAME)

  suspend fun updateUsername(value: String) {
    preferencesDataStore.putString(PreferenceKeys.Auth.NAME, value)
  }
}