package com.ilhomsoliev.gamehubandroid.app

import android.app.Application
import com.ilhomsoliev.gamehubandroid.core.di.appModule
import com.ilhomsoliev.gamehubandroid.core.di.dataStoreModule
import com.ilhomsoliev.gamehubandroid.core.di.databaseModule
import com.ilhomsoliev.gamehubandroid.core.di.networkModule
import com.ilhomsoliev.gamehubandroid.core.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class GameHubApplication : Application() {
  override fun onCreate() {
    super.onCreate()

    FlipperInitializer.init(this)

    startKoin {
      androidContext(this@GameHubApplication)
      modules(
        appModule,
        viewModelModule,
        dataStoreModule,
        networkModule,
        databaseModule,
      )
    }
  }
}
