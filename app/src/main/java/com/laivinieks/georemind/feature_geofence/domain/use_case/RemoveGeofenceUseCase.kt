package com.laivinieks.georemind.feature_geofence.domain.use_case

import com.google.android.gms.location.Geofence
import com.laivinieks.georemind.feature_geofence.domain.model.LocationGeofenceData
import com.laivinieks.georemind.feature_geofence.domain.repository.GeofenceRepository

class RemoveGeofenceUseCase  (private val repository: GeofenceRepository) {
    suspend operator fun invoke() {
        return repository.removeGeofenceUseCase()
    }

}