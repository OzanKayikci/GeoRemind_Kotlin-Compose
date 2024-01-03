package com.laivinieks.georemind.feature_geofence.domain.use_case

import com.google.android.gms.location.Geofence
import com.laivinieks.georemind.feature_geofence.domain.model.LocationGeofenceData
import com.laivinieks.georemind.feature_geofence.domain.repository.GeofenceRepository


class CreateGeofenceUseCase (private val repository: GeofenceRepository) {
    operator fun invoke(locationData: LocationGeofenceData, radius: Float): Geofence {
        return repository.createGeofence(locationData, radius)
    }
}