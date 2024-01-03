package com.laivinieks.georemind.feature_geofence.domain.repository

import com.google.android.gms.location.Geofence
import com.google.android.gms.tasks.Task
import com.laivinieks.georemind.feature_geofence.domain.model.LocationGeofenceData

interface GeofenceRepository {
    fun createGeofence(locationData: LocationGeofenceData,radius:Float): Geofence
    suspend fun addGeofence(geofenceList: List<Geofence>,onSuccess: () -> Unit, onFailure: () -> Unit): Task<Void>?
    suspend fun removeGeofenceUseCase()
}