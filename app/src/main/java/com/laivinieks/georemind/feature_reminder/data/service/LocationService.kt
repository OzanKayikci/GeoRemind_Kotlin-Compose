package com.laivinieks.georemind.feature_reminder.data.service

import android.app.Activity
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.location.Location
import android.os.IBinder
import android.util.Log
import android.widget.Toast

import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.VISIBILITY_PUBLIC
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.laivinieks.georemind.R
import com.laivinieks.georemind.core.domain.util.Constants
import com.laivinieks.georemind.feature_reminder.data.repository.LocationRepositoryImpl
import com.laivinieks.georemind.feature_reminder.domain.repository.LocationRepository
import com.laivinieks.georemind.feature_reminder.domain.service.LocationClient
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

import javax.inject.Inject

//@AndroidEntryPoint
//class LocationService @Inject constructor() : Service() {
//
//    @Inject
//    lateinit var locationRepository: LocationRepository
//
//    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
//
//    private var locationValues: Flow<Location?> = flowOf(null)
//    private val locationCallback = object : LocationCallback() {
//        override fun onLocationResult(locationResult: LocationResult) {
//            locationValues = flow {
//                emit(locationResult.lastLocation)
//            }
//        }
//    }
//
//    override fun onBind(intent: Intent?): IBinder? {
//        return null
//    }
//
//    override fun onCreate() {
//        super.onCreate()
//
//    }
//    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
//        when (intent?.action) {
//            ACTION_START -> start()
//            ACTION_STOP -> stop()
//        }
//        return super.onStartCommand(intent, flags, startId)
//
//    }
//
//    private fun start() {
//        val notification = NotificationCompat.Builder(this, Constants.LOCATION_NOTIFICATION_ID)
//            .setContentTitle("Tracking Location...")
//            .setContentText("Location : null")
//            .setSmallIcon(R.drawable.ic_bottom_location_add_focused)
//            .setOngoing(true)
//
//        startForeground(1, notification.build())
//
//        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//
//        serviceScope.launch {
//            locationRepository.startLocationUpdates(locationCallback)
//            var latitute = locationValues?.last()?.latitude
//            var longitude = locationValues?.last()?.latitude
//            val updatedNotification = notification.setContentText(
//                "Location  :$latitute - $longitude"
//            )
//
//            notificationManager.notify(1, updatedNotification.build())
//        }
//
//
//    }
//
//    private fun stop() {
//        stopForeground(STOP_FOREGROUND_REMOVE)
//        stopSelf()
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//
//        serviceScope.cancel()
//    }
//
//    companion object {
//        const val ACTION_START = "ACTION_START"
//        const val ACTION_STOP = "ACTION_STOP"
//
//        fun startService(context: Context) {
//            val intent = Intent(context, LocationService::class.java)
//            intent.action = ACTION_START
//            context.startService(intent)
//        }
//    }
//}
//

@AndroidEntryPoint
class LocationService : Service() {

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    @Inject
    lateinit var locationRepository: LocationRepository

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_START -> start()
            ACTION_STOP -> stop()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun start() {
        locationRepository.getLocationUpdates(interval = Constants.LOCATION_REQUEST_INTERVAL,
            scope = serviceScope,
            onFailure = {
                Toast.makeText(applicationContext, "Location settings are not satisfied. Please open location", Toast.LENGTH_LONG).show()

            }, onSuccess = {
                Log.d("in location service", it.toString())
            })


//        val notification = NotificationCompat.Builder(this, Constants.LOCATION_NOTIFICATION_ID)
//            .setContentTitle("Tracking location...")
//            .setContentText("Location: null")
//            .setSmallIcon(R.drawable.ic_bottom_location_add_focused)
//            .setPriority(NotificationCompat.PRIORITY_MAX)
//            .setDefaults(NotificationCompat.DEFAULT_ALL)
//            .setVisibility(VISIBILITY_PUBLIC)
//
//        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


//                val updatedNotification = notification.setContentText(
//                    "Location: ($lat, $long)"
//                )
//                notificationManager.notify(1, updatedNotification.build())


//        startForeground(1, notification.build())
    }

    private fun stop() {
        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }

    companion object {
        const val ACTION_START = "ACTION_START"
        const val ACTION_STOP = "ACTION_STOP"

        fun startService(context: Context) {
            val intent = Intent(context, LocationService::class.java)
            intent.action = ACTION_START
            context.startService(intent)
        }
    }
}