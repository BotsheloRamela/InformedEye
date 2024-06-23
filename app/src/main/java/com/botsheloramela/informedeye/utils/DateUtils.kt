package com.botsheloramela.informedeye.utils

/**
 * Returns the ordinal suffix for a given day of the month.
 *
 * This function determines the appropriate suffix ("st", "nd", "rd", "th")
 * for a given day of the month based on English ordinal rules.
 *
 * @param day the day of the month as an integer (1-31)
 * @return a string representing the ordinal suffix for the given day
 */
fun getDayOfMonthSuffix(day: Int): String {
    if (day in 11..13) {
        return "th"
    }
    return when (day % 10) {
        1 -> "st"
        2 -> "nd"
        3 -> "rd"
        else -> "th"
    }
}