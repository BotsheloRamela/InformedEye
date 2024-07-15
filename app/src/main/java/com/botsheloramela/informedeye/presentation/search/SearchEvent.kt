package com.botsheloramela.informedeye.presentation.search

/**
 * Represents the different events that can occur in the search screen.
 */
sealed class SearchEvent {
    data class UpdateSearchQuery(val query: String): SearchEvent()

    object Search: SearchEvent()
}