package com.botsheloramela.informedeye.presentation.search

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.botsheloramela.informedeye.domain.usecase.NewsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * ViewModel for the search screen.
 */
@HiltViewModel
class SearchViewModel @Inject constructor(
     private val newsUseCases: NewsUseCases
): ViewModel() {

     private val _state = mutableStateOf(SearchState())
     val state: State<SearchState> = _state

     fun onEvent(event: SearchEvent) {
          when (event) {
               is SearchEvent.UpdateSearchQuery -> {
                    _state.value = _state.value.copy(searchQuery = event.query)
               }
                is SearchEvent.Search -> {
                    searchNews()
                }
          }
     }

     private fun searchNews() {
          val articles = newsUseCases.searchNews(
               searchQuery = _state.value.searchQuery,
               sources = listOf("google-news", "bbc-news", "cnn")
          ).cachedIn(viewModelScope)
          _state.value = state.value.copy(articles = articles)
     }
}