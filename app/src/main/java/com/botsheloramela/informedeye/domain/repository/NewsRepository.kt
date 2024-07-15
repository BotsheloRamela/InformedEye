package com.botsheloramela.informedeye.domain.repository

import androidx.paging.PagingData
import com.botsheloramela.informedeye.domain.model.Article
import kotlinx.coroutines.flow.Flow

/**
 * Repository for news data.
 */
interface NewsRepository {
    fun getNews(sources: List<String>): Flow<PagingData<Article>>

    fun getHeadlines(): Flow<PagingData<Article>>

    fun searchNews(query: String, sources: List<String>): Flow<PagingData<Article>>
}