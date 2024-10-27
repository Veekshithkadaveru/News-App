package com.example.newsapp.ui.base

import android.content.Context
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.newsapp.ui.MainViewmodel
import com.example.newsapp.ui.headlines.bookmarks.BookmarksScreen
import com.example.newsapp.ui.headlines.home.HomeScreen
import com.example.newsapp.ui.headlines.offline.OfflineScreen
import com.example.newsapp.ui.headlines.search.SearchScreen

sealed class Route(val name: String) {
    data object HomeScreen : Route(name = "home_screen")
    data object SearchScreen : Route(name = "search_screen")
    data object BookmarksScreen : Route(name = "bookmarks_screen")
    data object OfflineScreen : Route(name = "offline_screen")
}

@Composable
fun AppNavHost(
    mainViewModel: MainViewmodel,
    customTabsIntent: CustomTabsIntent
) {
    val navController = rememberNavController()
    val context = LocalContext.current

    NavHost(
        navController = navController,
        startDestination = Route.HomeScreen.name
    ) {
        composable(route = Route.HomeScreen.name) {
            HomeScreen(
                context = context,
                navController = navController,
                mainViewmodel = mainViewModel
            ) {
                openCustomChromeTab(
                    customTabsIntent = customTabsIntent,
                    context = context,
                    url = it
                )
            }
        }
        composable(route = Route.SearchScreen.name) {
            SearchScreen(
                context = context,
                navController = navController,
                mainViewmodel = mainViewModel
            ) {
                openCustomChromeTab(
                    customTabsIntent = customTabsIntent,
                    context = context,
                    url = it
                )
            }
        }
        composable(route = Route.BookmarksScreen.name) {
            BookmarksScreen(context = context, navController = navController) {
                openCustomChromeTab(
                    customTabsIntent = customTabsIntent,
                    context = context,
                    url = it
                )
            }
        }
        composable(route = Route.OfflineScreen.name) {
            OfflineScreen(context = context, navController = navController) {
                openCustomChromeTab(
                    customTabsIntent = customTabsIntent,
                    context = context,
                    url = it
                )
            }
        }
    }
}

fun openCustomChromeTab(customTabsIntent: CustomTabsIntent, context: Context, url: String) {
    customTabsIntent.launchUrl(context, Uri.parse(url))
}