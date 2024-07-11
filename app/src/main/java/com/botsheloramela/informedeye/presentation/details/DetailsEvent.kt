package com.botsheloramela.informedeye.presentation.details

import com.botsheloramela.informedeye.domain.model.Article

sealed class DetailsEvent {
    data class UpsertDeleteArticle(val article: Article) : DetailsEvent()

    object RemoveSideEffect : DetailsEvent()
}