package com.botsheloramela.informedeye.domain.usecase

import androidx.paging.PagingData
import androidx.paging.filter
import com.botsheloramela.informedeye.domain.model.Article
import com.botsheloramela.informedeye.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.regex.Pattern

/**
 * Use case class for fetching news articles.
 *
 * Encapsulates the logic required to retrieve news articles from a repository.
 *
 * @property newsRepository The repository instance used to access news article data.
 */
class GetNewsUseCase(
    private val newsRepository: NewsRepository
) {
    /**
     * Invokes the use case to fetch news articles from the specified sources.
     *
     * @param sources A list of sources to fetch news articles from.
     * @return A Flow emitting PagingData<Article>, representing paginated news articles.
     */
    operator fun invoke(sources: List<String>): Flow<PagingData<Article>> {
        // Delegate the call to the repository to handle data retrieval
        return newsRepository.getNews(sources = sources).map { pagingData ->
            pagingData.filter { article ->
                !isTimestamp(article.title)
            }
        }
    }

    /**
     * Checks if the provided string is a timestamp.
     *
     * @param title The string to check.
     * @return True if the string is a timestamp, false otherwise.
     */
    private fun isTimestamp(title: String): Boolean {
        val timestampPattern = Pattern.compile(
            // Date patterns: yyyy-MM-dd, MM/dd/yyyy, dd/MM/yyyy
            "^\\d{4}[-/]\\d{2}[-/]\\d{2}( \\d{2}:\\d{2}(?::\\d{2})?)?( [A-Z]{2,4})?$|" + // yyyy-MM-dd or yyyy/MM/dd with optional time and timezone
                    "^\\d{2}[-/]\\d{2}[-/]\\d{4}( \\d{2}:\\d{2}(?::\\d{2})?)?( [A-Z]{2,4})?$"     // dd/MM/yyyy or MM/dd/yyyy with optional time and timezone
        )
        return timestampPattern.matcher(title).matches()
    }
}