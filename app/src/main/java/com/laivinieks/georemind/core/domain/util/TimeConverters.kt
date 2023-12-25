package com.laivinieks.georemind.core.domain.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Date

object  TimeConverters {
    @SuppressLint("SimpleDateFormat")
     fun convertTimeToTimestamp(hours: Int?, minutes: Int?): Long {
        val timeString = String.format("%02d:%02d", hours, minutes)
        val format = SimpleDateFormat("HH:mm")
        val date = format.parse(timeString)
        return date?.time ?: 0L
    }

    @SuppressLint("SimpleDateFormat")
     fun convertTimestampToTime(timestamp: Long): Pair<Int, Int> {
        val date = Date(timestamp)
        val format = SimpleDateFormat("HH:mm")
        val formattedTime = format.format(date)
        val (hours, minutes) = formattedTime.split(":").map { it.toInt() }
        return Pair(hours, minutes)
    }
}