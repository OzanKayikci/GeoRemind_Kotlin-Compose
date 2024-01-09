package com.laivinieks.georemind.feature_alarm.domain

import java.time.LocalDateTime

data class AlarmItem(
    val id: Int,
    val alarmTime: Long,
    val message: String,
    val title:String
)