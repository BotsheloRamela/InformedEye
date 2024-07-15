package com.botsheloramela.informedeye.domain.usecase

import androidx.paging.PagingData
import androidx.paging.filter
import com.botsheloramela.informedeye.domain.model.Article
import com.botsheloramela.informedeye.domain.repository.NewsRepository
import com.botsheloramela.informedeye.utils.TimeUtils.isTimestamp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Use case class for searching news articles.
 *
 * Encapsulates the logic required to search for news articles from a repository.
 *
 * @property newsRepository The repository instance used to access news article data.
 */
class SearchNewsUseCase(
    private val newsRepository: NewsRepository
) {
    operator fun invoke(searchQuery: String, sources: List<String>): Flow<PagingData<Article>> {
        // Delegate the call to the repository to handle data retrieval
        return newsRepository.searchNews(query = searchQuery ,sources = sources).map { pagingData ->
            pagingData.filter { article ->
                !isTimestamp(article.title)
            }
        }
    }
}