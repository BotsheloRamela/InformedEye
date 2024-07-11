package com.botsheloramela.informedeye.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.paging.compose.collectAsLazyPagingItems
import com.botsheloramela.informedeye.domain.model.Article
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
                navigateToDetails = { article ->
                    navigateToDetails(navController, article)
                }
            )
        }

        composable<Screen.Details> {
            val viewModel: DetailsViewModel = hiltViewModel()
            // TODO: Handle side effect
            navController.previousBackStackEntry?.savedStateHandle?.get<Article?>("article")?.let {article ->
                DetailsScreen(
                    article = article,
                    event = viewModel::onBookmarkArticle,
                    navigateUp = {
                        navController.popBackStack()
                    }
                )
            }

        }

        composable<Screen.Bookmarks> {
            val viewModel: BookmarkViewModel = hiltViewModel()
            BookmarksScreen(
                state = viewModel.state.value,
                navigateToDetails = { article ->
                    navigateToDetails(navController, article)
                }
            )
        }
    }
}

private fun navigateToDetails(navController: NavController, article: Article) {
    navController.currentBackStackEntry?.savedStateHandle?.set("article", article)
    navController.navigate(Screen.Details)
}