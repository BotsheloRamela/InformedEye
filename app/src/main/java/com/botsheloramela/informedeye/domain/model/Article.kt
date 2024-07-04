package com.botsheloramela.informedeye.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Article(
    val author: String? = null,
    val content: String,
    val description: String,
    val publishedAt: String,
    val source: Source,
    val title: String,
    val url: String,
    val urlToImage: String
)