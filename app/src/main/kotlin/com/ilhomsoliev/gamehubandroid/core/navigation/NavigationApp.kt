package com.ilhomsoliev.gamehubandroid.core.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.ilhomsoliev.gamehubandroid.core.ui.theme.AppTheme
import com.ilhomsoliev.gamehubandroid.feature.auth.presentation.AuthScreen
import com.ilhomsoliev.gamehubandroid.feature.details.presentation.GameDetailScreen
import com.ilhomsoliev.gamehubandroid.feature.home.presentation.HomeScreen
import com.ilhomsoliev.gamehubandroid.feature.library.presentation.LibraryScreen
import com.ilhomsoliev.gamehubandroid.feature.profile.presentation.ProfileScreen
import com.ilhomsoliev.gamehubandroid.feature.search.presentation.SearchScreen
import com.ilhomsoliev.gamehubandroid.feature.splash.SplashScreen

@Composable
fun NavigationApp(modifier: Modifier = Modifier) {

  val navController = rememberNavController()
  val navBackStackEntry by navController.currentBackStackEntryAsState()
  val currentRoute = navBackStackEntry?.destination?.route.orEmpty()
  val bottomNavRoutes = setOf(
    NavRoute.Home::class.qualifiedName.orEmpty(),
    NavRoute.Search::class.qualifiedName.orEmpty(),
    NavRoute.Library::class.qualifiedName.orEmpty(),
    NavRoute.Profile::class.qualifiedName.orEmpty()
  )
  val showBottomBar = bottomNavRoutes.any { currentRoute.startsWith(it) }

  Scaffold(
    modifier = Modifier.fillMaxSize(),
    containerColor = AppTheme.colors.background,
    bottomBar = {
      if (showBottomBar) {
        BottomNavigationComponent(
          navBackStackEntry = navBackStackEntry,
          navController = navController
        )
      }
    }
  ) { innerPadding ->

    NavHost(
      modifier = modifier.padding(innerPadding),
      navController = navController,
      startDestination = NavRoute.Splash
    ) {

      composable<NavRoute.Splash> {
        SplashScreen(onIsAuthorizedLoaded = { isAuthorized: Boolean ->
          if (isAuthorized) {
            navController.navigate(NavRoute.Home) {
              launchSingleTop = true
              restoreState = true
            }
          } else {
            navController.navigate(NavRoute.Auth) {
              launchSingleTop = true
              restoreState = true
            }
          }
        })
      }

      composable<NavRoute.Auth> {
        AuthScreen(onSuccessAuth = {
          navController.navigate(NavRoute.Home) {
            launchSingleTop = true
            restoreState = true
          }
        })
      }

      composable<NavRoute.Home> {
        HomeScreen(onOpenGameDetail = { gameId ->
          navController.navigate(NavRoute.GameDetail(gameId)) {
            launchSingleTop = true
          }
        }, onOpenSearch = { genreId, openedFromHomeSearch ->
          navController.navigate(NavRoute.Search(genreId, openedFromHomeSearch)) {
            popUpTo(navController.graph.findStartDestination().id) {
              saveState = true
            }
            launchSingleTop = true
            restoreState = true
          }
        })
      }

      composable<NavRoute.Search> { backStackEntry ->
        val route = backStackEntry.toRoute<NavRoute.Search>()
        SearchScreen(
          genreId = route.genreId,
          openedFromHomeSearch = route.openedFromHomeSearch,
          onOpenGameDetail = { gameId ->
            navController.navigate(NavRoute.GameDetail(gameId)) {
              launchSingleTop = true
            }
          },
          onBack = { navController.popBackStack() }
        )
      }

      composable<NavRoute.Library> {
        LibraryScreen(onOpenGameDetail = { gameId ->
          navController.navigate(NavRoute.GameDetail(gameId)) {
            launchSingleTop = true
          }
        })
      }

      composable<NavRoute.Profile> {
        ProfileScreen()
      }

      composable<NavRoute.GameDetail> { backStackEntry ->
        val route = backStackEntry.toRoute<NavRoute.GameDetail>()
        GameDetailScreen(
          gameId = route.id,
          onBack = {
            navController.popBackStack()
          },
          onOpenGameDetail = { gameId ->
            navController.navigate(NavRoute.GameDetail(gameId)) {
              launchSingleTop = true
            }
          }
        )
      }
    }
  }
}
