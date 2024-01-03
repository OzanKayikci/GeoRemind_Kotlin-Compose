package com.laivinieks.georemind.feature_geofence.presentation

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.laivinieks.georemind.R
import com.laivinieks.georemind.core.domain.util.Constants
import javax.inject.Inject

class NotificationHelper  @Inject constructor(private val context: Context) {

    fun showNotification(title:String,message: String) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Build your notification
        val notification = NotificationCompat.Builder(context, Constants.LOCATION_NOTIFICATION_ID)
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(R.drawable.ic_bottom_location_add_focused)
            .build()

        // Notify
        notificationManager.notify(1, notification)
    }
}