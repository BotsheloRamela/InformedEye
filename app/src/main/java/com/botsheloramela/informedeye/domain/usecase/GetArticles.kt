package com.botsheloramela.informedeye.domain.usecase

import com.botsheloramela.informedeye.data.local.NewsDao
import com.botsheloramela.informedeye.domain.model.Article
import kotlinx.coroutines.flow.Flow

class GetArticles(private val newsDao: NewsDao) {
    operator fun invoke(): Flow<List<Article>> {
        return newsDao.getArticles()
    }
}