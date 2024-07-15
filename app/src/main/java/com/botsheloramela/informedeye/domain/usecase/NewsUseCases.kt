package com.botsheloramela.informedeye.domain.usecase

data class NewsUseCases(
    val getNews: GetNewsUseCase,
    val getTopHeadlines: GetTopHeadlinesUseCase,
    val upsertArticle: UpsertArticle,
    val deleteArticle: DeleteArticle,
    val getArticles: GetArticles,
    val getArticle: GetArticle
)
