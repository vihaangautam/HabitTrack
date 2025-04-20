package com.example.habittrack.logic.utils

import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

object Calculations {

    // Formats current time to string: "dd/MM/yyyy HH:mm"
    private fun getCurrentTimestamp(): String {
        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        return sdf.format(Date())
    }

    // Convert selected date & time to relative "X minutes/hours ago" or "Starts in X..."
    fun calculateTimeBetweenDates(startDate: String): String {
        val endDate = getCurrentTimestamp()

        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        val date1 = sdf.parse(startDate)
        val date2 = sdf.parse(endDate)

        var isNegative = false
        var difference = date2.time - date1.time

        if (difference < 0) {
            difference = -difference
            isNegative = true
        }

        val minutes = TimeUnit.MILLISECONDS.toMinutes(difference)
        val hours = TimeUnit.MILLISECONDS.toHours(difference)
        val days = TimeUnit.MILLISECONDS.toDays(difference)
        val months = days / 30
        val years = days / 365

        return if (isNegative) {
            when {
                minutes < 240 -> "Starts in $minutes minutes"
                hours < 48 -> "Starts in $hours hours"
                days < 61 -> "Starts in $days days"
                months < 24 -> "Starts in $months months"
                else -> "Starts in $years years"
            }
        } else {
            when {
                minutes < 240 -> "$minutes minutes ago"
                hours < 48 -> "$hours hours ago"
                days < 61 -> "$days days ago"
                months < 24 -> "$months months ago"
                else -> "$years years ago"
            }
        }
    }

    // Format date to dd/MM/yyyy
    fun cleanDate(day: Int, month: Int, year: Int): String {
        val dayStr = if (day < 10) "0$day" else "$day"
        val monthAdjusted = month + 1 // Calendar.MONTH is 0-based
        val monthStr = if (monthAdjusted < 10) "0$monthAdjusted" else "$monthAdjusted"
        return "$dayStr/$monthStr/$year"
    }

    // Format time to HH:mm
    fun cleanTime(hour: Int, minute: Int): String {
        val hourStr = if (hour < 10) "0$hour" else "$hour"
        val minuteStr = if (minute < 10) "0$minute" else "$minute"
        return "$hourStr:$minuteStr"
    }
}
