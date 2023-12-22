package com.laivinieks.georemind.feature_reminder.data.service

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.IntentSender
import android.os.Looper
import android.util.Log
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Granularity
import com.google.android.gms.location.LocationAvailability
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.Priority
import com.laivinieks.georemind.core.domain.util.Constants
import com.laivinieks.georemind.feature_reminder.domain.service.LocationManager

@SuppressLint("MissingPermission")
class LocationManagerImp(
    private val context: Context,
    private var timeInterval: Long,
    private var minimalDistance: Float,

    ) : LocationCallback(), LocationManager {

    private var request: LocationRequest
    private var locationClient: FusedLocationProviderClient
    private var locationCallback: LocationCallback? = null

    init {
        // getting the location client
        locationClient = LocationServices.getFusedLocationProviderClient(context)
        request = createRequest()
    }

    private fun createRequest(): LocationRequest =
        // New builder
        LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, timeInterval).apply {
            setMinUpdateDistanceMeters(minimalDistance)
            setGranularity(Granularity.GRANULARITY_PERMISSION_LEVEL)
            setWaitForAccurateLocation(true)
        }.build()

    override fun changeRequest(timeInterval: Long, minimalDistance: Float) {
        this.timeInterval = timeInterval
        this.minimalDistance = minimalDistance
        createRequest()
        stopLocationTracking()
        startLocationTracking()
    }

    override fun checkLocationSettings(onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        val locationSettingsBuilder = LocationSettingsRequest.Builder().addLocationRequest(request)
        val locationSettingsClient = LocationServices.getSettingsClient(context)

        val task = locationSettingsClient.checkLocationSettings(locationSettingsBuilder.build())

        task.addOnSuccessListener {
            // All location settings are satisfied. The client can initialize location requests here.
            onSuccess.invoke()
        }

        task.addOnFailureListener { exception ->
            Log.d("EXCEPTION", exception.toString())
            onFailure.invoke(exception)
        }
    }

    override fun setLocationCallback(callback: LocationCallback) {
        locationCallback = callback
    }

    override fun startLocationTracking() {
        locationCallback?.let {
            locationClient.requestLocationUpdates(request, it, Looper.getMainLooper())
        }
    }

    override fun stopLocationTracking() {
        locationClient.flushLocations()
        locationClient.removeLocationUpdates(this)
    }

    override fun onLocationResult(location: LocationResult) {
        locationCallback?.onLocationResult(location)
    }

    override fun onLocationAvailability(availability: LocationAvailability) {
        locationCallback?.onLocationAvailability(availability)
    }

}