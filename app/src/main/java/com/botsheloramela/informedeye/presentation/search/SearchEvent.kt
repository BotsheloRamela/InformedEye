package com.botsheloramela.informedeye.presentation.search

sealed class SearchEvent {

    data class UpdateSearchQuery(val query: String): SearchEvent()

    object Search: SearchEvent()
}