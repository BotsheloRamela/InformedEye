package com.botsheloramela.informedeye.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import androidx.paging.compose.collectAsLazyPagingItems
import com.botsheloramela.informedeye.domain.model.Article
import com.botsheloramela.informedeye.domain.model.Source
import com.botsheloramela.informedeye.presentation.details.DetailsScreen
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
                    navController.navigate(
                        Screen.Details(
                            author = article.author,
                            content = article.content,
                            description = article.description,
                            publishedAt = article.publishedAt,
                            sourceId = article.source.id,
                            sourceName = article.source.name,
                            title = article.title,
                            url = article.url,
                            urlToImage = article.urlToImage
                        )
                    )
                }
            )
        }

        composable<Screen.Details> { backStackEntry ->
            val args = backStackEntry.toRoute<Screen.Details>()
            DetailsScreen(
                article = Article(
                    author = args.author,
                    content = args.content,
                    description = args.description,
                    publishedAt = args.publishedAt,
                    source = Source(
                        id = args.sourceId,
                        name = args.sourceName
                    ),
                    title = args.title,
                    url = args.url,
                    urlToImage = args.urlToImage
                ),
                event = {},
                navigateUp = {
                    navController.popBackStack()
                }
            )
        }
    }
}