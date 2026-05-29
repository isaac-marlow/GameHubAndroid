package com.ilhomsoliev.gamehubandroid.core.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.ilhomsoliev.gamehubandroid.core.local.datastore.PreferencesDataStore
import com.ilhomsoliev.gamehubandroid.core.local.datastore.PreferencesDataStoreImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

/**
 * DataStore module for providing DataStore instance for key-value storage.
 *
 * Usage:
 * - Inject PreferencesDataStore in your classes for type-safe access
 * - Use PreferenceKeys object to define your preference keys
 */
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "app_preferences")

val dataStoreModule = module {
  single<DataStore<Preferences>> {
    androidContext().dataStore
  }

  single<PreferencesDataStore> {
    PreferencesDataStoreImpl(get())
  }
}
