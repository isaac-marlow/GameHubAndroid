package com.ilhomsoliev.gamehubandroid.core.navigation

  import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.LibraryBooks
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.ilhomsoliev.gamehubandroid.core.ui.theme.AppTheme

private data class BottomNavItem(
  val label: String,
  val icon: ImageVector,
  val destination: NavRoute
)

@Composable
fun BottomNavigationComponent(
  navBackStackEntry: NavBackStackEntry?,
  navController: NavHostController
) {
  val items = listOf(
    BottomNavItem("Home", Icons.Default.Home, NavRoute.Home),
    BottomNavItem("Search", Icons.Default.Search, NavRoute.Search()),
    BottomNavItem("Library", Icons.AutoMirrored.Filled.LibraryBooks, NavRoute.Library),
    BottomNavItem("Profile", Icons.Default.Person, NavRoute.Profile)
  )

  val currentRoute = navBackStackEntry?.destination?.route.orEmpty()

  NavigationBar(containerColor = AppTheme.colors.surfaceLow) {
    items.forEach { item ->
      val itemRoute = item.destination::class.qualifiedName.orEmpty()
      val selected = currentRoute.startsWith(itemRoute)

      NavigationBarItem(
        selected = selected,
        onClick = {
          if (!selected) {
            navController.navigate(item.destination) {
              popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
              }
              launchSingleTop = true
              restoreState = true
            }
          }
        },
        icon = {
          Icon(
            imageVector = item.icon,
            contentDescription = item.label,
          )
        },
        label = {
          Text(
            text = item.label,
            style = AppTheme.typography.bodyLarge
          )
        },
        colors = NavigationBarItemDefaults.colors(
          selectedIconColor = AppTheme.colors.primary,
          selectedTextColor = AppTheme.colors.onSurface,
          unselectedIconColor = AppTheme.colors.onSurfaceVar,
          unselectedTextColor = AppTheme.colors.onSurfaceVar,
          indicatorColor = AppTheme.colors.secondary
        )
      )
    }
  }
}