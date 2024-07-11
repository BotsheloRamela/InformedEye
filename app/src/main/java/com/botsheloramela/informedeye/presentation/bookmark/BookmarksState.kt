package com.botsheloramela.informedeye.presentation.bookmark

import com.botsheloramela.informedeye.domain.model.Article

/**
 * State for the bookmark screen
 */
data class BookmarksState(
    val articles: List<Article> = emptyList(),
)
