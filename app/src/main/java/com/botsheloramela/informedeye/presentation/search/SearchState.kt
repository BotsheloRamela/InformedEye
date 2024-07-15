package com.botsheloramela.informedeye.presentation.search

import androidx.paging.PagingData
import com.botsheloramela.informedeye.domain.model.Article
import kotlinx.coroutines.flow.Flow

/**
 * Represents the state of the search screen.
 *
 * @param searchQuery The search query entered by the user.
 * @param articles A Flow of PagingData<Article> representing the search results.
 */
data class SearchState(
    val searchQuery: String = "",
    val articles: Flow<PagingData<Article>>? = null,
)