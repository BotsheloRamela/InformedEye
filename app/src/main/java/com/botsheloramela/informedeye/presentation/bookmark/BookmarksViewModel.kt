package com.botsheloramela.informedeye.presentation.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.botsheloramela.informedeye.domain.usecase.NewsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

/**
 * ViewModel for the bookmark screen.
 */
@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val newsUseCases: NewsUseCases
) : ViewModel() {

    private val _state = MutableStateFlow(BookmarksState())
    val state: StateFlow<BookmarksState> = _state

    init {
        getArticles()
    }

    private fun getArticles() {
        newsUseCases.newsArticleManager.selectArticles()
            .onEach { _state.value = _state.value.copy(articles = it) }
            .launchIn(viewModelScope)
    }
}