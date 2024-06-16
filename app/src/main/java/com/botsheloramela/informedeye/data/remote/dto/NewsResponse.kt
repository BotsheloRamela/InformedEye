package com.botsheloramela.informedeye.data.remote.dto

import com.botsheloramela.informedeye.domain.model.Article

data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)