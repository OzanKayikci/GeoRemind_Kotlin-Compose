package com.laivinieks.georemind.core.domain.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Date

object  TimeConverters {
    @SuppressLint("SimpleDateFormat")
     fun convertTimeToTimestamp(hours: Int?, minutes: Int?,date:Long?): Long {
        val timeString = String.format("%02d:%02d", hours, minutes)
        val format = SimpleDateFormat("HH:mm")
        val timeParsed = format.parse(timeString)
        val timestamp = date?.plus(timeParsed.time)
        return timestamp ?: 0L
    }

    @SuppressLint("SimpleDateFormat")
     fun convertTimestampToTime(timestamp: Long): Triple<Int, Int,String> {
        val date = Date(timestamp)
        val formatHour = SimpleDateFormat("HH:mm")
        val formatDate = SimpleDateFormat("dd.MM.yy")
        val formattedTime = formatHour.format(date)
        val formattedDate = formatDate.format(date)
        val (hours, minutes) = formattedTime.split(":").map { it.toInt() }

        return Triple(hours, minutes,formattedDate)
    }
}