package com.botsheloramela.informedeye.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.botsheloramela.informedeye.domain.model.Article
import com.botsheloramela.informedeye.presentation.Dimensions.ExtraSmallPadding2
import com.botsheloramela.informedeye.presentation.Dimensions.MediumPadding1

@Composable
fun TopArticlesList(
    modifier: Modifier = Modifier,
    articles: LazyPagingItems<Article>,
    onClick: (Article) -> Unit
) {
    val handleHeadlinesPagingResult = handleHeadlinesPagingResult(articles = articles)

    if (handleHeadlinesPagingResult) {
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(MediumPadding1),
            contentPadding = PaddingValues(horizontal = ExtraSmallPadding2)
        ) {
            items(count = 5) {
                articles[it]?.let {
                    TopArticleCard(article = it)
                }
            }
        }
    }
}

@Composable
fun handleHeadlinesPagingResult(
    articles: LazyPagingItems<Article>,
): Boolean {
    val loadState = articles.loadState
    val error = when {
        loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
        loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
        loadState.append is LoadState.Error -> loadState.append as LoadState.Error
        else -> null
    }

    return when {
        loadState.refresh is LoadState.Loading -> {
            // Show shimmer effect while refreshing
            ShimmerEffect()
            false
        }
        error!= null -> {
            // Handle error and show error screen
//            ErrorScreen()
            false
        }
        else -> {
            // No loading or error, proceed normally
            true
        }
    }
}

@Composable
private fun ShimmerEffect() {
    // Render shimmer effect for placeholder loading state
    Row(horizontalArrangement = Arrangement.spacedBy(MediumPadding1)) {
        repeat(5) {
            TopArticleCardShimmerEffect(
                modifier = Modifier.padding(horizontal = MediumPadding1)
            )
        }
    }
}