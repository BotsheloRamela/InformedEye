package com.botsheloramela.informedeye.presentation.details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.botsheloramela.informedeye.domain.model.Article
import com.botsheloramela.informedeye.domain.usecase.NewsUseCases
import com.botsheloramela.informedeye.presentation.components.UIComponent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for the DetailsScreen.
 *
 * @property newsUseCases The NewsUseCases instance to use for fetching news articles.
 */
@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val newsUseCases: NewsUseCases
): ViewModel() {

    private val _sideEffect = MutableSharedFlow<UIComponent?>(replay = 1)
    val sideEffect: SharedFlow<UIComponent?> = _sideEffect

    fun onBookmarkArticle(event: DetailsEvent) {
        when (event) {
            is DetailsEvent.UpsertDeleteArticle -> {
                viewModelScope.launch {
                    val article = newsUseCases.newsArticleManager.selectArticle(event.article.url)
                    if (article == null) {
                        upsertArticle(event.article)
                    } else {
                        deleteArticle(event.article)
                    }
                }
            }

            is DetailsEvent.RemoveSideEffect -> {
                _sideEffect.tryEmit(null)
            }
        }
    }

    private suspend fun deleteArticle(article: Article) {
        runCatching {
            newsUseCases.newsArticleManager.deleteArticle(article)
            _sideEffect.emit(UIComponent.Toast("Article removed from bookmarks"))
        }.onFailure { error ->
            _sideEffect.emit(UIComponent.Dialog("Error", error.message ?: "An unexpected error occurred"))
            Log.e("DetailsViewModel", "deleteArticle: ${error.message}", error)
        }
    }

    private suspend fun upsertArticle(article: Article) {
        runCatching {
            newsUseCases.newsArticleManager.upsertArticle(article)
            _sideEffect.emit(UIComponent.Toast("Article added to bookmarks"))
        }.onFailure { error ->
            _sideEffect.emit(UIComponent.Dialog("Error", error.message ?: "An unexpected error occurred"))
            Log.e("DetailsViewModel", "upsertArticle: ${error.message}", error)
        }
    }
}