package com.laivinieks.georemind.feature_geofence.data

import android.Manifest
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.Task
import com.laivinieks.georemind.feature_geofence.presentation.GeofenceBroadcastReceiver
import javax.inject.Inject

class GeofenceManager @Inject constructor(
    private val context: Context
) {

    fun addGeofence(geofenceList: List<Geofence>, onSuccess: () -> Unit, onFailure: () -> Unit): Task<Void>? {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return null
        }
        val geofencingClient = LocationServices.getGeofencingClient(context)

        return geofencingClient.addGeofences(createGeofencingRequest(geofenceList), getGeofencePendingIntent())
            .addOnSuccessListener { onSuccess.invoke() }
            .addOnFailureListener { onFailure.invoke() }
    }

    fun removeGeofence(){
        val geofencingClient = LocationServices.getGeofencingClient(context)
        geofencingClient.removeGeofences(getGeofencePendingIntent())
            .addOnSuccessListener {
                Log.d("geofence","remove sucecss")
            }
            .addOnFailureListener {
                Log.d("geofence","remove fail")
            }
    }

    private fun createGeofencingRequest(geofenceList: List<Geofence>): GeofencingRequest {
        return GeofencingRequest.Builder().apply {
            setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
            addGeofences(geofenceList)
        }.build()
    }

    private fun getGeofencePendingIntent(): PendingIntent {
        val intent = Intent(context, GeofenceBroadcastReceiver::class.java)

        return PendingIntent.getBroadcast(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
        )
    }
}