package com.botsheloramela.informedeye.presentation.details

import com.botsheloramela.informedeye.domain.model.Article

/**
 * Represents the different events that can occur in the details screen.
 */
sealed class DetailsEvent {
    data class UpsertDeleteArticle(val article: Article) : DetailsEvent()

    object RemoveSideEffect : DetailsEvent()
}