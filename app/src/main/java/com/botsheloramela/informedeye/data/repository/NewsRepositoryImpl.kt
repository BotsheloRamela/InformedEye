package com.botsheloramela.informedeye.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.botsheloramela.informedeye.data.remote.NewsApi
import com.botsheloramela.informedeye.data.remote.NewsArticlePagingSource
import com.botsheloramela.informedeye.domain.model.Article
import com.botsheloramela.informedeye.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

/**
 * Implementation of the NewsRepository interface.
 *
 * Fetches news articles from the API and provides them as a flow of paginated data.
 *
 * @property newsApi The API service used to fetch news articles.
 */
class NewsRepositoryImpl(
    private val newsApi: NewsApi
): NewsRepository {

    /**
     * Retrieves news articles from the specified sources and returns them as a flow of paginated data.
     *
     * @param sources A list of sources to fetch news articles from.
     * @return A Flow emitting PagingData<Article>, representing paginated news articles.
     */
    override fun getNews(sources: List<String>): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(pageSize = 10), // Define the size of pages to be loaded
            // Create a new instance of NewsArticlePagingSource for each pager
            pagingSourceFactory = {
                NewsArticlePagingSource(
                    newsApi = newsApi,
                    sources = sources.joinToString(separator = ",") // Combine sources into a single string
                )
            }
        ).flow
    }
}