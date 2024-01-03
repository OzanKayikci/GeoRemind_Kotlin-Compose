package com.laivinieks.georemind.feature_geofence.domain.use_case

data class GeofenceUseCases(
    val addGeofenceUseCase: AddGeofenceUseCase,
    val createGeofenceUseCase: CreateGeofenceUseCase,
    val removeGeofenceUseCase: RemoveGeofenceUseCase
)