package com.laivinieks.georemind.feature_reminder.data.repository

import android.content.Context
import android.location.Location
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationServices
import com.laivinieks.georemind.feature_reminder.domain.repository.LocationRepository
import com.laivinieks.georemind.feature_reminder.domain.service.LocationManager
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val locationManager: LocationManager
) : LocationRepository {
    override suspend fun startLocationUpdates(callback: LocationCallback) {
        locationManager.setLocationCallback(callback)
        locationManager.startLocationTracking()
    }

    override suspend fun stopLocationUpdates(callback: LocationCallback) {
        locationManager.stopLocationTracking()
    }

    override suspend fun checkLocationSettings(onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        locationManager.checkLocationSettings(onSuccess, onFailure)
    }


}