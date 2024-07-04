package com.botsheloramela.informedeye.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.paging.compose.collectAsLazyPagingItems
import com.botsheloramela.informedeye.presentation.home.HomeScreen
import com.botsheloramela.informedeye.presentation.home.HomeViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: Screen = Screen.Home,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable<Screen.Home> {
            val viewModel: HomeViewModel = hiltViewModel()
            val articles = viewModel.news.collectAsLazyPagingItems()
            val topHeadlines = viewModel.headlines.collectAsLazyPagingItems()

            HomeScreen(
                articles = articles,
                topHeadlines = topHeadlines,
                navigateToDetails = { }
            )
        }
    }
}

val NavHostController.canGoBack: Boolean
    get() = this.currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED