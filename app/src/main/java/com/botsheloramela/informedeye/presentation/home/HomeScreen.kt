package com.botsheloramela.informedeye.presentation.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.compose.LazyPagingItems
import com.botsheloramela.informedeye.R
import com.botsheloramela.informedeye.domain.model.Article
import com.botsheloramela.informedeye.presentation.Dimensions.ExtraSmallPadding2
import com.botsheloramela.informedeye.presentation.Dimensions.MediumPadding1
import com.botsheloramela.informedeye.presentation.Dimensions.MediumPadding2
import com.botsheloramela.informedeye.presentation.components.ArticlesList
import com.botsheloramela.informedeye.presentation.components.TopArticlesList
import com.botsheloramela.informedeye.utils.getDayOfMonthSuffix
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(
    articles: LazyPagingItems<Article>,
    topHeadlines: LazyPagingItems<Article>,
    navigateToDetails: (Article) -> Unit
) {
    val currentDate = LocalDate.now()
    val day = currentDate.dayOfMonth
    val suffix = getDayOfMonthSuffix(day)
    val formatter = DateTimeFormatter.ofPattern("EEEE, MMMM d'$suffix'", Locale.getDefault())
    val formattedDate = currentDate.format(formatter)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_logo),
                            contentDescription = null,
                            modifier = Modifier.size(32.dp),
                            tint = MaterialTheme.colorScheme.primary,
                        )
                        Spacer(modifier = Modifier.width(ExtraSmallPadding2))
                        Text(
                            text = "InformedEye",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = MaterialTheme.typography.titleLarge.fontWeight,
                        )
                    }
                }
            )
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(horizontal = ExtraSmallPadding2)
                .padding(top = MediumPadding1)
        ) {
            Spacer(modifier = Modifier.padding(MediumPadding2))

            Text(
                text = formattedDate,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.secondary
            )

            Text(
                text = "Welcome back",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = MaterialTheme.typography.titleLarge.fontWeight,
            )
            Spacer(modifier = Modifier.height(MediumPadding1))
            // TODO: Top headlines carousal
            TopArticlesList(
                modifier = Modifier.padding(horizontal = MediumPadding1),
                articles = topHeadlines,
                onClick = navigateToDetails
            )
            Spacer(modifier = Modifier.height(MediumPadding1))
            ArticlesList(
                modifier = Modifier.padding(horizontal = MediumPadding1),
                articles = articles,
                onClick = navigateToDetails
            )
        }
    }
}