package com.botsheloramela.informedeye.navigation

import kotlinx.serialization.Serializable

/**
 * Represents the different screens in the app.
 */
@Serializable
sealed class Screen {
    companion object {
        fun fromRoute(route: String): Screen? {
            return Screen::class.sealedSubclasses.firstOrNull {
                route.contains(it.qualifiedName.toString())
            }?.objectInstance
        }
    }
    @Serializable
    data object Home : Screen()

    @Serializable
    data object Details : Screen()

    @Serializable
    data object Bookmarks : Screen()

    @Serializable
    data object Search : Screen()

}