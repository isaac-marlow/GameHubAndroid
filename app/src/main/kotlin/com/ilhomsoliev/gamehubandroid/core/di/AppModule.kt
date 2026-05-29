package com.ilhomsoliev.gamehubandroid.core.di

import com.ilhomsoliev.gamehubandroid.feature.auth.data.AuthRepository
import com.ilhomsoliev.gamehubandroid.feature.details.data.RedditApi
import com.ilhomsoliev.gamehubandroid.feature.details.data.RedditRepository
import com.ilhomsoliev.gamehubandroid.feature.game.data.GameApi
import com.ilhomsoliev.gamehubandroid.feature.game.data.GameRepository
import com.ilhomsoliev.gamehubandroid.feature.library.data.LibraryRepository
import com.ilhomsoliev.gamehubandroid.feature.search.data.FiltersRepository
import com.ilhomsoliev.gamehubandroid.feature.search.data.SearchRepository
import com.ilhomsoliev.gamehubandroid.feature.search.data.network.FilterApi
import org.koin.dsl.module

val appModule = module {
  // API
  single { AuthRepository(get()) }

  single { GameRepository(get()) }
  single { LibraryRepository(get()) }
  single { SearchRepository(get()) }
  single { FiltersRepository(get(), get()) }
  single { RedditRepository(get()) }

  single { GameApi(get()) }
  single { FilterApi(get()) }
  single { RedditApi(get()) }
  /*

  // Repository
  Module.single<SampleRepository> { SampleRepositoryImpl(Scope.get(), Scope.get()) }

  // UseCases
  Module.factory { GetSampleItemsUseCase(Scope.get()) }

  // ViewModels
  viewModel { SampleViewModel(Scope.get()) }*/
}
