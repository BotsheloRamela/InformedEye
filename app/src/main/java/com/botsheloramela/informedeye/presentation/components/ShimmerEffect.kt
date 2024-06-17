package com.botsheloramela.informedeye.presentation.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.botsheloramela.informedeye.R
import com.botsheloramela.informedeye.presentation.Dimensions
import com.botsheloramela.informedeye.presentation.Dimensions.MediumPadding1
import com.botsheloramela.informedeye.ui.theme.InformedEyeTheme

/**
 * Custom Composable modifier to apply a shimmer effect to a UI element.
 *
 * Creates an animated background color change to simulate a shimmer effect.
 *
 * @receiver Modifier The receiver to which this modifier is applied.
 * @return Modifier The modified modifier with the shimmer effect applied.
 */
fun Modifier.shimmerEffect() = composed {
    // Remember an infinite transition to animate the alpha value
    val transition = rememberInfiniteTransition(label = "")

    // Animate the alpha value between 0.2f and 0.9f to create the shimmer effect
    val alpha = transition.animateFloat(
        initialValue = 0.2f, 
        targetValue = 0.9f,
        label = "",
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = RepeatMode.Reverse
        )
    )

    // Apply the animated alpha value to the background color
    background(color = colorResource(id = R.color.shimmer).copy(alpha = alpha.value))
}

@Composable
fun ArticleCardShimmerEffect(
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {

        Column(
            verticalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .padding(horizontal = Dimensions.ExtraSmallPadding)
                .height(Dimensions.ArticleCardSize)
                .weight(1f)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp)
                    .padding(horizontal = MediumPadding1)
                    .shimmerEffect()
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .height(15.dp)
                        .padding(horizontal = MediumPadding1)
                        .shimmerEffect()
                )
            }
        }

        Box(
            modifier = Modifier
                .size(Dimensions.ArticleCardSize)
                .clip(RoundedCornerShape(24.dp))
                .shimmerEffect()
        )
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun ArticleCardShimmerEffectPreview() {
    InformedEyeTheme {
        ArticleCardShimmerEffect()
    }
}