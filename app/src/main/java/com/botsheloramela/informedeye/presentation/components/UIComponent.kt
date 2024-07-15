package com.botsheloramela.informedeye.presentation.components

/**
 * Represents the different UI components that can be displayed to the user.
 */
sealed class UIComponent {
    data class Toast(val message: String): UIComponent()
    data class Dialog(val title: String, val message: String): UIComponent()
}