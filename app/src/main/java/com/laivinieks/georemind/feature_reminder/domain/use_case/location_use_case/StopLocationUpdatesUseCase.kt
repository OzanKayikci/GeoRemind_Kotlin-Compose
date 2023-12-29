package com.laivinieks.georemind.feature_reminder.domain.use_case.location_use_case

import android.content.Context
import android.location.Location
import com.google.android.gms.location.LocationCallback
import com.laivinieks.georemind.feature_reminder.domain.repository.LocationRepository
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class StopLocationUpdatesUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) {
    operator fun invoke() {
        locationRepository.stopLocationTracking()
    }
}