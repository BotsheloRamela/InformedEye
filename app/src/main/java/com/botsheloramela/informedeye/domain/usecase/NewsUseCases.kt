package com.botsheloramela.informedeye.domain.usecase

/**
 * Represents the use cases related to news.
 *
 * @param getNews The use case to get news.
 * @param getTopHeadlines The use case to get top headlines.
 * @param newsArticleManager The manager for news articles in the database.
 * @param searchNews The use case to search news.
 */
data class NewsUseCases(
    val getNews: GetNewsUseCase,
    val getTopHeadlines: GetTopHeadlinesUseCase,
    val newsArticleManager: NewsArticleManager,
    val searchNews: SearchNewsUseCase
)
