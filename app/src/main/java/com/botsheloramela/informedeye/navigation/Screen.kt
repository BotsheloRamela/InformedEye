package com.botsheloramela.informedeye.navigation

import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    data object Home : Screen()
}