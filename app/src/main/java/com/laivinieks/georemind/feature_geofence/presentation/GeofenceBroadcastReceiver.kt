package com.laivinieks.georemind.feature_geofence.presentation

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofenceStatusCodes
import com.google.android.gms.location.GeofencingEvent
import com.google.gson.Gson
import com.laivinieks.georemind.core.presentation.NotificationHelper
import com.laivinieks.georemind.feature_geofence.domain.model.LocationDataForReminder

import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class GeofenceBroadcastReceiver : BroadcastReceiver() {

    @Inject
    lateinit var notificationHelper: NotificationHelper
    override fun onReceive(context: Context?, intent: Intent) {
        Log.d("geofence", "onReceive")
        // Handle geofence transition events here
        val geofencingEvent = GeofencingEvent.fromIntent(intent)
        if (geofencingEvent!!.hasError()) {
            val errorMessage = GeofenceStatusCodes
                .getStatusCodeString(geofencingEvent.errorCode)
            Log.e("geofence", errorMessage)
            return
        }

        val geofenceTransition = geofencingEvent.geofenceTransition
        when (geofenceTransition) {
            Geofence.GEOFENCE_TRANSITION_ENTER -> {
                Log.d("geofence", "Entered")
                // Get the geofences that were triggered. A single event can trigger
                // multiple geofences.
                val triggeringGeofences = geofencingEvent.triggeringGeofences

                var notificationTitle = "Entered Location"
                var notificationMessage = " Your selected Location"
                triggeringGeofences?.let { geofences ->
                    val locationData = Gson().fromJson(geofences.first().requestId, LocationDataForReminder::class.java)

                    notificationTitle = locationData.reminderTitle
                    notificationMessage ="Entered ${locationData.locationName}"
                }

                // User entered the geofence, show notification or perform actions
                notificationHelper.showNotification(notificationTitle, notificationMessage)

            }

        }

    }

}