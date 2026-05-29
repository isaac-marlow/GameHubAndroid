package com.ilhomsoliev.gamehubandroid.core.di

import com.ilhomsoliev.gamehubandroid.feature.auth.presentation.AuthViewModel
import com.ilhomsoliev.gamehubandroid.feature.details.presentation.GameDetailViewModel
import com.ilhomsoliev.gamehubandroid.feature.home.presentation.HomeViewModel
import com.ilhomsoliev.gamehubandroid.feature.library.presentation.LibraryViewModel
import com.ilhomsoliev.gamehubandroid.feature.search.presentation.SearchViewModel
import com.ilhomsoliev.gamehubandroid.feature.splash.SplashViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
  viewModelOf(::SplashViewModel)
  viewModelOf(::AuthViewModel)
  viewModelOf(::HomeViewModel)
  viewModelOf(::SearchViewModel)
  viewModelOf(::GameDetailViewModel)
  viewModelOf(::LibraryViewModel)
}