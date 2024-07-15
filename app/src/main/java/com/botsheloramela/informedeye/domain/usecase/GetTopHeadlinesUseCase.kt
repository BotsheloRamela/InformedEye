package com.botsheloramela.informedeye.domain.usecase

import androidx.paging.PagingData
import com.botsheloramela.informedeye.domain.model.Article
import com.botsheloramela.informedeye.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

/**
 * Use case class for fetching top headlines.
 *
 * Encapsulates the logic required to retrieve top headlines from a repository.
 *
 * @property newsRepository The repository instance used to access news article data.
 */
class GetTopHeadlinesUseCase(
    private val newsRepository: NewsRepository
) {
    /**
     * Invokes the use case to fetch top headlines.
     *
     * @return A Flow emitting PagingData<Article>, representing paginated news articles.
     */
    operator fun invoke(): Flow<PagingData<Article>> {
        return newsRepository.getHeadlines();
    }
}