package com.laivinieks.georemind.feature_geofence.domain.use_case

import com.google.android.gms.location.Geofence

import com.laivinieks.georemind.feature_geofence.domain.repository.GeofenceRepository

class AddGeofenceUseCase(private val repository: GeofenceRepository) {
    suspend operator fun invoke(geofenceList: List<Geofence>,onSuccess: () -> Unit, onFailure: () -> Unit) {
        repository.addGeofence(geofenceList,onSuccess, onFailure)
    }
}