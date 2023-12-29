package com.laivinieks.georemind.feature_reminder.domain.repository

import android.app.Activity
import android.content.Context
import android.location.Location
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow

interface LocationRepository {
    fun getLocationUpdates(interval: Long, scope: CoroutineScope,  onSuccess: (Location) -> Unit, onFailure : (LocationRequest?)->Unit): Job
    fun stopLocationTracking()
}


//interface LocationRepository {
//    suspend fun startLocationUpdates(callback: LocationCallback)
//    suspend fun stopLocationUpdates(callback: LocationCallback)
//    suspend fun checkLocationSettings(onSuccess: () -> Unit, onFailure: (Exception) -> Unit)
//}