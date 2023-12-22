package com.laivinieks.georemind.feature_reminder.domain.service

import com.google.android.gms.location.LocationCallback
import com.google.android.gms.tasks.Task

interface LocationManager {

    fun setLocationCallback(callback: LocationCallback)
    fun startLocationTracking()
    fun stopLocationTracking()
    fun changeRequest(timeInterval: Long, minimalDistance: Float)
    fun checkLocationSettings(onSuccess: () -> Unit, onFailure: (Exception) -> Unit)
}