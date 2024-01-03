package com.laivinieks.georemind.feature_reminder.data.repository

import android.location.Location
import com.google.android.gms.location.LocationRequest
import com.laivinieks.georemind.feature_reminder.domain.repository.LocationRepository
import com.laivinieks.georemind.feature_reminder.domain.service.LocationClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

//class LocationRepositoryImpl @Inject constructor(
//    private val locationManager: LocationManager
//) : LocationRepository {
//    override suspend fun startLocationUpdates(callback: LocationCallback) {
//        locationManager.setLocationCallback(callback)
//        locationManager.startLocationTracking()
//    }
//
//    override suspend fun stopLocationUpdates(callback: LocationCallback) {
//        locationManager.stopLocationTracking()
//    }
//
//    override suspend fun checkLocationSettings(onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
//        locationManager.checkLocationSettings(onSuccess, onFailure)
//    }
//
//
//}

class LocationRepositoryImpl @Inject constructor(
    private val locationClient: LocationClient
) : LocationRepository {
    override fun getLocationUpdates(
        interval: Long,
        scope: CoroutineScope,
        onSuccess: (Location) -> Unit,
        onFailure: (LocationRequest?) -> Unit
    ): Job {
        return locationClient.getLocationUpdates(interval, locationRequest = {
            onFailure.invoke(it)
        }).catch {
            onFailure.invoke(null)

        }.onEach {
            onSuccess.invoke(it)
        }.launchIn(scope)

    }

    override fun stopLocationTracking() {
        locationClient.stopLocationTracking()
    }
}