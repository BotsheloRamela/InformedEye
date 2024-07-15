package com.botsheloramela.informedeye.domain.usecase

import com.botsheloramela.informedeye.data.local.NewsDao
import com.botsheloramela.informedeye.domain.model.Article

class DeleteArticle (private val newsDao: NewsDao)  {
    suspend operator fun invoke(article: Article){
        newsDao.delete(article = article)
    }
}