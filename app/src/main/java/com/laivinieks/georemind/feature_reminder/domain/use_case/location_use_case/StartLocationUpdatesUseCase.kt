package com.laivinieks.georemind.feature_reminder.domain.use_case.location_use_case

import com.google.android.gms.location.LocationCallback
import com.laivinieks.georemind.feature_reminder.domain.repository.LocationRepository
import javax.inject.Inject

class StartLocationUpdatesUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) {
    suspend operator fun invoke(callback: LocationCallback) {
        locationRepository.startLocationUpdates(callback)
    }
}


