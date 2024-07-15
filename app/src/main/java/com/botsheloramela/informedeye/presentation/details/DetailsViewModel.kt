package com.botsheloramela.informedeye.presentation.details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.botsheloramela.informedeye.domain.model.Article
import com.botsheloramela.informedeye.domain.usecase.NewsUseCases
import com.botsheloramela.informedeye.utils.UIComponent
import dagger.hilt.android.lifecycle.HiltViewModel
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

    var sideEffect by mutableStateOf<UIComponent?>(null)
        private set

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
                sideEffect = null
            }
        }
    }

    private suspend fun deleteArticle(article: Article) {
        newsUseCases.newsArticleManager.deleteArticle(article)
        sideEffect = UIComponent.Toast("Article removed from bookmarks")
    }

    private suspend fun upsertArticle(article: Article) {
        newsUseCases.newsArticleManager.upsertArticle(article)
        sideEffect = UIComponent.Toast("Article added to bookmarks")
    }
}