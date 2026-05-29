package com.ilhomsoliev.gamehubandroid.core.di

import com.ilhomsoliev.gamehubandroid.core.local.database.GameHubDatabaseHelper
import org.koin.dsl.module

val databaseModule = module {
  single { GameHubDatabaseHelper(get()) }
}
