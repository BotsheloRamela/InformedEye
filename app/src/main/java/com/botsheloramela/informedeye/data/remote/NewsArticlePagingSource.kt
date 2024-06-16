package com.botsheloramela.informedeye.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.botsheloramela.informedeye.domain.model.Article

/**
 * A PagingSource that fetches news articles from the API.
 *
 * @property newsApi The API service used to fetch news articles.
 * @property sources The sources parameter for the API request.
 */
class NewsArticlePagingSource(
    private val newsApi: NewsApi,
    private val sources: String,
): PagingSource<Int, Article>() {

    private var totalFetchedArticlesCount  = 0;

    /**
     * Loads a page of news articles based on the provided parameters.
     *
     * @param params Parameters including the key (page number) for loading.
     * @return A LoadResult object indicating success or failure.
     */
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val pageNumber  = params.key ?: 1

        return try {
            val newsResponse = newsApi.getNews(sources = sources, page = pageNumber)
            totalFetchedArticlesCount += newsResponse.articles.size

            // Filter out duplicate articles based on title
            val uniqueArticles = newsResponse.articles.distinctBy { it.title }

            LoadResult.Page(
                data = uniqueArticles,
                // Determine the next page number unless we've reached the last page
                nextKey = if (totalFetchedArticlesCount >= newsResponse.totalResults) null else pageNumber + 1,
                prevKey = null // Previous key is not supported in this implementation
            )
        } catch (e: Exception) {
            e.printStackTrace()
            LoadResult.Error(
                throwable = e
            )
        }
    }

    /**
     * Provides a refresh key for the PagingState, allowing for efficient data refresh.
     *
     * @param state The current PagingState.
     * @return An optional refresh key (Int), or null if not available.
     */
    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        // Calculate the refresh key based on the closest page to the anchor position
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}