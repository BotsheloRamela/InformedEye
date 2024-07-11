package com.botsheloramela.informedeye.presentation.components

import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.botsheloramela.informedeye.R
import com.botsheloramela.informedeye.domain.model.Article
import com.botsheloramela.informedeye.domain.model.Source
import com.botsheloramela.informedeye.presentation.Dimensions.ExtraSmallPadding
import com.botsheloramela.informedeye.presentation.Dimensions.ExtraSmallPadding2
import com.botsheloramela.informedeye.presentation.Dimensions.MediumPadding1
import com.botsheloramela.informedeye.ui.theme.InformedEyeTheme
import com.botsheloramela.informedeye.utils.RandomPlaceholderImageUtil

@Composable
fun TopArticleCard(
    modifier: Modifier = Modifier,
    article: Article
) {
    val context = LocalContext.current

    Box(modifier = modifier.clickable {
        Intent(Intent.ACTION_VIEW).also {
            it.data = Uri.parse(article.url)
            if (it.resolveActivity(context.packageManager) != null) {
                context.startActivity(it)
            }
        }
    }) {
        Card(
            modifier = modifier
                .clip(RoundedCornerShape(20.dp))
                .height(200.dp)
                .width(350.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.Black.copy(
                    alpha = 0.5f
                ),
            ),
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                val randomDrawableRes = RandomPlaceholderImageUtil.getRandomDrawableResource()
                AsyncImage(
                    modifier = Modifier.fillMaxSize(),
                    model = ImageRequest.Builder(context).data(article.urlToImage).build(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    alpha = 0.3f,
                    error = painterResource(id = randomDrawableRes)
                )

                Column(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(MediumPadding1),
                ) {
                    Text(
                        text = article.title,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                        ),
                        color = Color.White,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.width(250.dp)
                    )

                    Spacer(modifier = Modifier.height(ExtraSmallPadding2))

                    Text(
                        text = article.source.name,
                        color = MaterialTheme.colorScheme.secondary,
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Bold,
                        ),
                        modifier = Modifier.padding(bottom = ExtraSmallPadding)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun TopArticleCardPreview() {
    InformedEyeTheme {
        TopArticleCard(
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
        )
    }
}