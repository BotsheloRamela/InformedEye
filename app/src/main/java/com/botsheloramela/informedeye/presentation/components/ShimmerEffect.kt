package com.botsheloramela.informedeye.presentation.components

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.res.colorResource
import com.botsheloramela.informedeye.R

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