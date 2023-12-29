package com.laivinieks.georemind.feature_reminder.domain.use_case.location_use_case

import android.app.Activity
import android.content.Context
import android.location.Location
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.laivinieks.georemind.feature_reminder.domain.repository.LocationRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.launchIn
import javax.inject.Inject

class StartLocationUpdatesUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) {
     operator fun invoke(interval:Long, scope: CoroutineScope, onSuccess: (Location) -> Unit, onFailure : (LocationRequest?)->Unit) {
        locationRepository.getLocationUpdates(interval,scope,onSuccess, onFailure)
    }
}


