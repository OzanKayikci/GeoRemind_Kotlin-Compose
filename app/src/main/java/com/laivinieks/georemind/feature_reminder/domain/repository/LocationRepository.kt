package com.laivinieks.georemind.feature_reminder.domain.repository

import android.location.Location
import com.google.android.gms.location.LocationCallback

interface LocationRepository {
    suspend fun startLocationUpdates(callback: LocationCallback)
    suspend fun stopLocationUpdates(callback: LocationCallback)
}