package com.botsheloramela.informedeye.utils

import androidx.annotation.DrawableRes
import com.botsheloramela.informedeye.R
import kotlin.random.Random

/**
 * Utility class for selecting a random placeholder image from a predefined list.
 *
 * This class provides a method to retrieve a random drawable resource ID from a set of predefined placeholders.
 * It's useful for scenarios where a random image needs to be displayed, such as loading states or error images.
 */
object RandomPlaceholderImageUtil {
    private val drawableIds = intArrayOf(
        R.drawable.placeholder1,
        R.drawable.placeholder2,
        R.drawable.placeholder3,
        R.drawable.placeholder4,
        R.drawable.placeholder5,
        R.drawable.placeholder6,
        R.drawable.placeholder7,
    )

    /**
     * Retrieves a random drawable resource ID from the predefined list of placeholder images.
     *
     * @return An integer representing the drawable resource ID of a randomly selected placeholder image.
     */
    @DrawableRes
    fun getRandomDrawableResource(): Int {
        val randomIndex = Random.nextInt(drawableIds.size)
        return drawableIds[randomIndex]
    }
}