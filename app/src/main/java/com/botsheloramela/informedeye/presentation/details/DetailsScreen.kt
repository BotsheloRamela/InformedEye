package com.botsheloramela.informedeye.presentation.details

import android.content.Intent
import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.botsheloramela.informedeye.domain.model.Article
import com.botsheloramela.informedeye.domain.model.Source
import com.botsheloramela.informedeye.presentation.Dimensions.ArticleImageHeight
import com.botsheloramela.informedeye.presentation.Dimensions.ExtraSmallPadding3
import com.botsheloramela.informedeye.presentation.Dimensions.MediumPadding1
import com.botsheloramela.informedeye.presentation.details.components.DetailsTopAppBar
import com.botsheloramela.informedeye.ui.theme.InformedEyeTheme
import com.botsheloramela.informedeye.utils.RandomPlaceholderImageUtil
import com.botsheloramela.informedeye.utils.TimeUtils

@Composable
fun DetailsScreen(
    article: Article,
    event: (DetailsEvent) -> Unit,
    navigateUp: () -> Unit
) {
    val context = LocalContext.current
    val randomDrawableRes = RandomPlaceholderImageUtil.getRandomDrawableResource()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            DetailsTopAppBar(
                onBackClick = navigateUp,
                onShareClick = {
                    Intent(Intent.ACTION_SEND).also {
                        it.putExtra(Intent.EXTRA_TEXT, article.url)
                        it.type = "text/plain"
                        if (it.resolveActivity(context.packageManager) != null) {
                            context.startActivity(it)
                        }
                    }
                },
                onBookmarkClick = { event(DetailsEvent.SaveArticle) }
            )
        }

    ) { padding ->
        // Content below the app bar
        LazyColumn(
            modifier = Modifier.fillMaxWidth().padding(padding),
        ) {
            item {
                AsyncImage(
                    model = ImageRequest.Builder(context).data(article.urlToImage).build(),
                    contentDescription = null,
                    error = painterResource(id = randomDrawableRes),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(ArticleImageHeight)
                        .clip(MaterialTheme.shapes.medium.copy(bottomEnd = CornerSize(16.dp), bottomStart = CornerSize(16.dp))),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(MediumPadding1))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = MediumPadding1),
                ) {
                    Text(
                        text = article.title,
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight(500),
                        fontSize = 28.sp,
                        lineHeight = 32.sp
                    )

                    Spacer(modifier = Modifier.height(ExtraSmallPadding3))

                    Text(
                        text = "${TimeUtils.formatTimestamp(article.publishedAt)} | ${article.source.name} ${ if (article.author != null) "| ${article.author}" else "" }",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary,
                    )

                    Spacer(modifier = Modifier.height(ExtraSmallPadding3))

                    Text(
                        text = article.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                }
            }
        }

    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DetailsScreenPreview() {
    InformedEyeTheme {
        DetailsScreen(
            article = Article(
                title = "Title",
                content = "Content",
                urlToImage = "https://www.example.com",
                url = "https://www.example.com",
                description = "Description",
                source = Source(id = "", name = "bbc"),
                author = "John Doe",
                publishedAt = "2024-09-15T00:00:00Z",
            ),
            event = {},
            navigateUp = {}
        )
    }
}