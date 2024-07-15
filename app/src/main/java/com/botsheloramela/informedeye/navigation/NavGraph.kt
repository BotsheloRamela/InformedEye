package com.botsheloramela.informedeye.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.paging.compose.collectAsLazyPagingItems
import com.botsheloramela.informedeye.R
import com.botsheloramela.informedeye.domain.model.Article
import com.botsheloramela.informedeye.navigation.components.BottomNavItem
import com.botsheloramela.informedeye.navigation.components.BottomNavigation
import com.botsheloramela.informedeye.presentation.bookmark.BookmarkViewModel
import com.botsheloramela.informedeye.presentation.bookmark.BookmarksScreen
import com.botsheloramela.informedeye.presentation.details.DetailsScreen
import com.botsheloramela.informedeye.presentation.details.DetailsViewModel
import com.botsheloramela.informedeye.presentation.home.HomeScreen
import com.botsheloramela.informedeye.presentation.home.HomeViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: Screen = Screen.Home,
) {
    val bottomNavItems = remember {
        listOf(
            BottomNavItem(Screen.Home, R.drawable.ic_home, "Home"),
            BottomNavItem(Screen.Bookmarks, R.drawable.ic_bookmark, "Bookmarks")
        )
    }

    val backStackEntry= navController.currentBackStackEntryAsState().value
    var currentRoute = remember(backStackEntry) {
        Screen.fromRoute(backStackEntry?.destination?.route ?: "")
    }

    currentRoute = when (backStackEntry?.destination?.route) {
        Screen.Home.toString() -> Screen.Home
        Screen.Details.toString() -> Screen.Details
        else -> currentRoute
    }

    // Hide the bottom navigation when the user is in the details screen
    val showBottomNav = currentRoute != Screen.Details

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (showBottomNav) {
                BottomNavigation(
                    items = bottomNavItems,
                    onItemClick = { index ->
                        val screen = bottomNavItems[index].route
                        navigateToTab(navController, screen)
                    },
                    selectedItem = currentRoute
                )
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.fillMaxSize(),
        ) {
            composable<Screen.Home> {
                val viewModel: HomeViewModel = hiltViewModel()
                val articles = viewModel.news.collectAsLazyPagingItems()
                val topHeadlines = viewModel.headlines.collectAsLazyPagingItems()

                HomeScreen(
                    articles = articles,
                    topHeadlines = topHeadlines,
                    navigateToDetails = { article ->
                        navigateToDetails(navController, article)
                    }
                )
            }

            composable<Screen.Details> {
                val viewModel: DetailsViewModel = hiltViewModel()
                navController.previousBackStackEntry?.savedStateHandle?.get<Article?>("article")
                    ?.let { article ->
                        DetailsScreen(
                            article = article,
                            event = viewModel::onBookmarkArticle,
                            sideEffect = viewModel.sideEffect,
                            navigateUp = {navController.popBackStack()}
                        )
                    }
            }

            composable<Screen.Bookmarks> {
                val viewModel: BookmarkViewModel = hiltViewModel()
                val state = viewModel.state.value
                BookmarksScreen(
                    state = state,
                    navigateToDetails = { article ->
                        navigateToDetails(navController, article)
                    }
                )
            }
        }
    }
}

/* Navigate to the details screen */
private fun navigateToDetails(navController: NavController, article: Article) {
    navController.currentBackStackEntry?.savedStateHandle?.set("article", article)
    navController.navigate(Screen.Details)
}

/* Navigate to the selected tab */
private fun navigateToTab(navController: NavController, screen: Screen) {
    navController.navigate(screen) {
        navController.graph.startDestinationRoute?.let { route ->
            popUpTo(route) {
                saveState = true
            }
        }
        launchSingleTop = true
        restoreState = true
    }
}