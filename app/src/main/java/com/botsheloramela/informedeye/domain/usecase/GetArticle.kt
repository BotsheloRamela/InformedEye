package com.botsheloramela.informedeye.domain.usecase

import com.botsheloramela.informedeye.data.local.NewsDao
import com.botsheloramela.informedeye.domain.model.Article

class GetArticle(private val newsDao: NewsDao) {
    suspend operator fun invoke(url: String): Article? {
        return newsDao.getArticle(url = url)
    }
}