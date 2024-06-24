package com.botsheloramela.informedeye.domain.usecase

import androidx.paging.PagingData
import com.botsheloramela.informedeye.domain.model.Article
import com.botsheloramela.informedeye.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class GetTopHeadlinesUseCase(
    private val newsRepository: NewsRepository
) {
    /**
     * Invokes the use case to fetch top headlines.
     *
     * @return A Flow emitting PagingData<Article>, representing paginated news articles.
     */
    operator fun invoke(): Flow<PagingData<Article>> {
        // Delegate the call to the repository to handle data retrieval
        return newsRepository.getHeadlines();
    }
}