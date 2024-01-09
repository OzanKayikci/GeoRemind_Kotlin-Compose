package com.laivinieks.georemind.feature_alarm.data

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.laivinieks.georemind.core.domain.util.TimeConverters.convertLocalTimeToUtc
import com.laivinieks.georemind.feature_alarm.domain.AlarmItem
import com.laivinieks.georemind.feature_alarm.domain.AlarmScheduler
import com.laivinieks.georemind.feature_alarm.presentation.AlarmReceiver
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.TimeZone

class AlarmSchedulerImp(private val context: Context) : AlarmScheduler {

    private val alarmManager = context.getSystemService(AlarmManager::class.java)

    @SuppressLint("ScheduleExactAlarm")
    override fun schedule(alarmItem: AlarmItem) {

       val localAlarmTime =  convertLocalTimeToUtc(alarmItem.alarmTime)

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            localAlarmTime,
            getAlarmPendingIntent(alarmItem)
        )
        Log.e("Alarm", "Alarm set at ${alarmItem.alarmTime},$localAlarmTime")
    }

    override fun cancel(alarmItem: AlarmItem) {
        alarmManager.cancel(
            getAlarmPendingIntent(alarmItem)
        )
    }

    private fun getAlarmPendingIntent(alarmItem: AlarmItem): PendingIntent {
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            action ="ALARM_ACTION_${alarmItem.id}"
            putExtra("ALARM_MESSAGE_${alarmItem.id}", alarmItem.message)
            putExtra("ALARM_TITLE_${alarmItem.id}", alarmItem.title)
        }

        return PendingIntent.getBroadcast(
            context,
            alarmItem.id,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }
}