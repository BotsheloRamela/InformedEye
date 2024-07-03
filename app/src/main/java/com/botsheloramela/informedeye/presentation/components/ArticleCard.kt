package com.botsheloramela.informedeye.presentation.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.botsheloramela.informedeye.domain.model.Article
import com.botsheloramela.informedeye.domain.model.Source
import com.botsheloramela.informedeye.presentation.Dimensions.ArticleCardSize
import com.botsheloramela.informedeye.presentation.Dimensions.ExtraSmallPadding
import com.botsheloramela.informedeye.presentation.Dimensions.ExtraSmallPadding2
import com.botsheloramela.informedeye.presentation.Dimensions.ExtraSmallPadding3
import com.botsheloramela.informedeye.ui.theme.InformedEyeTheme
import com.botsheloramela.informedeye.utils.RandomPlaceholderImageUtil
import com.botsheloramela.informedeye.utils.TimeUtils

@Composable
fun ArticleCard(
    modifier: Modifier = Modifier,
    article: Article,
    onClick: () -> Unit,
) {
    val context = LocalContext.current
    val randomDrawableRes = RandomPlaceholderImageUtil.getRandomDrawableResource()

    Row(modifier = modifier.clickable { onClick() }) {
        Column(
            verticalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .padding(horizontal = ExtraSmallPadding)
                .height(ArticleCardSize)
                .weight(1f)
        ) {
            Text(
                text = article.title,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = article.source.name,
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(bottom = ExtraSmallPadding)
                )
                Spacer(modifier = Modifier.width(ExtraSmallPadding2))
                Text(
                    text = TimeUtils.formatTimestamp(article.publishedAt),
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(bottom = ExtraSmallPadding)
                )
            }
        }

        Spacer(modifier = Modifier.width(ExtraSmallPadding3))

        AsyncImage(
            modifier = Modifier
                .size(ArticleCardSize)
                .clip(RoundedCornerShape(24.dp)),
            model = ImageRequest.Builder(context).data(article.urlToImage).build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            error = painterResource(id = randomDrawableRes)
        )
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun ArticleCardPreview() {
    InformedEyeTheme {
        ArticleCard(
            article = Article(
                title = "Title",
                description = "Description",
                urlToImage = "https://example.com/image.jpg",
                url = "https://example.com",
                author = "",
                content = "",
                publishedAt = "2 hours",
                source = Source("id", "name")
            ),
            onClick = {}
        )
    }
}