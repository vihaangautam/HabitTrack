package com.example.habittrack.utils

import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun CalculateTimeInBetween(startDate: String): String {
    val endDate: String = Calculation.timeStampToString(System.currentTimeMillis()) // Fixed reference
    val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())

    val date1 = sdf.parse(startDate) ?: return "Invalid Start Date"
    val date2 = sdf.parse(endDate) ?: return "Invalid End Date"

    var difference: Long = date2.time - date1.time
    val isNegative = difference < 0

    if (isNegative) {
        difference = -difference
    }

    val minutes = difference / (60 * 1000)
    val hours = difference / (60 * 60 * 1000)
    val days = difference / (24 * 60 * 60 * 1000)
    val months = difference / (30L * 24 * 60 * 60 * 1000)  // Approximate month
    val years = difference / (365L * 24 * 60 * 60 * 1000)  // Approximate year

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

object Calculation {
    fun timeStampToString(timeStamp: Long): String {
        val stamp = Timestamp(timeStamp)
        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        return sdf.format(Date(stamp.time))
    }

    fun CleanDate(_day: Int, _month: Int, _year: Int): String {
        val day = if (_day < 10) "0$_day" else _day.toString()
        val month = if (_month < 9) "0${_month + 1}" else (_month + 1).toString() // Adjusting for zero-based months
        return "$day/$month/$_year"
    }

    fun cleanTime(_hour: Int, _minute: Int): String {
        val hour = if (_hour < 10) "0$_hour" else _hour.toString()
        val minute = if (_minute < 10) "0$_minute" else _minute.toString()
        return "$hour:$minute"
    }
}
