package com.botsheloramela.informedeye

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.botsheloramela.informedeye.ui.theme.InformedEyeTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.botsheloramela.informedeye.presentation.home.HomeScreen
import com.botsheloramela.informedeye.presentation.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.Serializable

@AndroidEntryPoint
class MainActivity: ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InformedEyeTheme() {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Home
                    ) {
                        composable<Home> {
                            val viewModel: HomeViewModel = hiltViewModel()
                            val articles = viewModel.news.collectAsLazyPagingItems()
                            val topHeadlines = viewModel.headlines.collectAsLazyPagingItems()

                            HomeScreen(articles = articles, topHeadlines = topHeadlines)
                        }
                    }
                }
            }
        }
    }
}

@Serializable
object Home