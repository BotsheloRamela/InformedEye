package com.botsheloramela.informedeye.domain.usecase

data class NewsUseCases(
    val getNews: GetNewsUseCase,
    val getTopHeadlines: GetTopHeadlinesUseCase,
    val newsArticleManager: NewsArticleManager
)
