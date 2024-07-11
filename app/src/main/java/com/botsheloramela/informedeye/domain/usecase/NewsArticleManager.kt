package com.botsheloramela.informedeye.domain.usecase

import com.botsheloramela.informedeye.data.local.NewsDao
import com.botsheloramela.informedeye.domain.model.Article
import kotlinx.coroutines.flow.Flow

class NewsArticleManager(
    private val newsDao: NewsDao
) {
    // Upsert operation
    suspend fun upsertArticle(article: Article) {
        newsDao.upsert(article)
    }

    // Select operation
    fun selectArticles(): Flow<List<Article>> {
        return newsDao.getArticles()
    }

    // Delete operation
    suspend fun deleteArticle(article: Article) {
        newsDao.delete(article)
    }
}