package com.example.habittrack.utils

import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.SimpleTimeZone

fun CalculateTimeInBetween(startDate: String): String{
    val endDate:String = timeStampToString(System.currentTimeMillis())
    val sdf= SimpleDateFormat("dd/MM/yyyy HH:mm")
    val date1 =sdf.parse(startDate)
    val date2=sdf.parse(endDate)

    var isNegative = false
    var difference:Long = date2.time- date1.time

    if(difference<0){
        difference=-(difference)
        isNegative=true
    }
    val minutes: Long = difference/60/1000
    val hours: Long = difference/60/1000/60
    val days:Long=(difference/60/1000/60)/24
    val months:Long=(difference/60/1000/60)/24/(365/12)
    val years:Long=difference/60/1000/60/24/365

    if(isNegative){
        return when{
            minutes<240 -> "Starts in $minutes minutes"
            hours<48 -> "Starts in $hours hours"
        }
    }

}

object Calculation {
    private fun timeStampToString(timeStamp: Long): String{
        val stamp=Timestamp(timeStamp)
        val sdf= SimpleDateFormat("dd/MM/yyyy HH:mm")
        val date:String =sdf.format((Date(stamp.time)))
        return date.toString()

    }
}