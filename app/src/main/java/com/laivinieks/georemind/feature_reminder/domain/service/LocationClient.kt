package com.laivinieks.georemind.feature_reminder.domain.service

import android.location.Location
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.LocationRequest

import kotlinx.coroutines.flow.Flow

interface LocationClient {
    fun getLocationUpdates(interval: Long,locationRequest:(LocationRequest)->Unit): Flow<Location>
    class LocationException(message: String): Exception()
    fun stopLocationTracking()
}