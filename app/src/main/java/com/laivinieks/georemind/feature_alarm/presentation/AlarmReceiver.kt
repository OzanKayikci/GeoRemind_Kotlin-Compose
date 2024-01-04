package com.laivinieks.georemind.feature_alarm.presentation


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.laivinieks.georemind.core.presentation.NotificationHelper
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AlarmReceiver : BroadcastReceiver() {

    @Inject
    lateinit var notificationHelper: NotificationHelper
    override fun onReceive(context: Context?, intent: Intent?) {

        Log.d("alarm", "onRecive")

        val action = intent?.action
        // Extract the alarmItem id from the action
        val alarmItemId = action?.substringAfterLast("_")?.toIntOrNull() ?: -1
        val message = intent?.getStringExtra("ALARM_MESSAGE_${alarmItemId}") ?: return
        val title = intent?.getStringExtra("ALARM_TITLE_${alarmItemId}") ?: return

        //show notification
        notificationHelper.showNotification(title, message)

    }
}