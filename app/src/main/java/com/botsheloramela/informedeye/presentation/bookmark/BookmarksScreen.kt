package com.botsheloramela.informedeye.presentation.bookmark

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.botsheloramela.informedeye.domain.model.Article
import com.botsheloramela.informedeye.presentation.Dimensions.MediumPadding1
import com.botsheloramela.informedeye.presentation.components.BookmarkedArticlesList

@Composable
fun BookmarkScreen(
    state: BookmarksState,
    navigateToDetails: (Article) -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(MediumPadding1)
            ) {
                Text(
                    text = "Bookmarks",
                    style = MaterialTheme.typography.displayMedium.copy(fontWeight = FontWeight.Bold)
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(padding)
        ) {
//            Text(
//                text = "Bookmarks",
//                style = MaterialTheme.typography.displayMedium.copy(fontWeight = FontWeight.Bold)
//            )

            Spacer(modifier = Modifier.padding(MediumPadding1))

            BookmarkedArticlesList(
                articles = state.articles,
                onClick = navigateToDetails
            )
        }
    }
}