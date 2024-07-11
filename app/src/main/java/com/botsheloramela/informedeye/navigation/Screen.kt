package com.botsheloramela.informedeye.navigation

import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    data object Home : Screen()
    @Serializable
    data class Details(
        val author: String?,
        val content: String,
        val description: String,
        val publishedAt: String,
        val sourceId: String,
        val sourceName: String,
        val title: String,
        val url: String,
        val urlToImage: String
    ) : Screen()

}