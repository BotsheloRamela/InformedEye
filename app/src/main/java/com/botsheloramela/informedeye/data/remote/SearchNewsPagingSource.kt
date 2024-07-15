package com.botsheloramela.informedeye.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.botsheloramela.informedeye.domain.model.Article

/**
 * A PagingSource that uses the NewsApi to fetch news articles based on the provided search query.
 *
 * @param newsApi The NewsApi instance to use for fetching news articles.
 * @param searchQuery The search query to use for fetching news articles.
 * @param sources The sources to use for fetching news articles.
 */
class SearchNewsPagingSource(
    private val newsApi: NewsApi,
    private val searchQuery: String,
    private val sources: String
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
            val newsResponse = newsApi.searchNews(searchQuery = searchQuery, sources = sources, page = pageNumber)
            totalFetchedArticlesCount += newsResponse.articles.size

            // Assuming top headlines also have titles, filter out duplicates similarly
            val uniqueArticles = newsResponse.articles.distinctBy { it.title }

            LoadResult.Page(
                data = uniqueArticles,
                nextKey = if (totalFetchedArticlesCount >= newsResponse.totalResults) null else pageNumber + 1,
                prevKey = null
            )
        } catch (e: Exception) {
            e.printStackTrace()
            LoadResult.Error(throwable = e)
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