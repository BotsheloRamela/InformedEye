package com.botsheloramela.informedeye.utils

import java.text.SimpleDateFormat
import java.time.ZoneId
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone
import java.util.regex.Pattern
import kotlin.math.abs

/**
 * Utility class for handling time-related operations.
 */
object TimeUtils {
    private const val DATE_FORMAT_DAY_MONTH_YEAR = "dd MMM yyyy"

    /**
     * Formats the given timestamp to a readable format.
     *
     * @param timestamp The timestamp string in ISO 8601 format.
     * @return A formatted string representing the timestamp.
     */
    fun formatTimestamp(timestamp: String): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        dateFormat.timeZone = TimeZone.getTimeZone(ZoneId.systemDefault())
        val date = dateFormat.parse(timestamp) ?: return ""

        val calendar = Calendar.getInstance()
        calendar.time = date

        val now = Calendar.getInstance()

        return if (now.get(Calendar.YEAR) == calendar.get(Calendar.YEAR) &&
            now.get(Calendar.DAY_OF_YEAR) == calendar.get(Calendar.DAY_OF_YEAR)
        ) {
            // Today's date
            val diffHours = abs(now.get(Calendar.HOUR_OF_DAY) - calendar.get(Calendar.HOUR_OF_DAY))
            val diffMinutes = abs(now.get(Calendar.MINUTE) - calendar.get(Calendar.MINUTE))

            when {
                diffHours > 0 -> "$diffHours ${if (diffHours == 1) "hour" else "hours"} ago"
                diffMinutes > 0 -> "$diffMinutes ${if (diffMinutes == 1) "minute" else "minutes"} ago"
                else -> "Just now"
            }
        } else {
            // Older dates
            SimpleDateFormat(DATE_FORMAT_DAY_MONTH_YEAR, Locale.getDefault()).format(date)
        }
    }

    /**
     * Checks if the provided string is a timestamp.
     *
     * @param title The string to check.
     * @return True if the string is a timestamp, false otherwise.
     */
    fun isTimestamp(title: String): Boolean {
        val timestampPattern = Pattern.compile(
            // Date patterns: yyyy-MM-dd, MM/dd/yyyy, dd/MM/yyyy
            "^\\d{4}[-/]\\d{2}[-/]\\d{2}( \\d{2}:\\d{2}(?::\\d{2})?)?( [A-Z]{2,4})?$|" + // yyyy-MM-dd or yyyy/MM/dd with optional time and timezone
                    "^\\d{2}[-/]\\d{2}[-/]\\d{4}( \\d{2}:\\d{2}(?::\\d{2})?)?( [A-Z]{2,4})?$"     // dd/MM/yyyy or MM/dd/yyyy with optional time and timezone
        )
        return timestampPattern.matcher(title).matches()
    }
}