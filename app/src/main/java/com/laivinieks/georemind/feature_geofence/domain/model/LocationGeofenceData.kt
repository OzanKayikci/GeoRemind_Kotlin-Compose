package com.laivinieks.georemind.feature_geofence.domain.model

data class LocationGeofenceData(
    val id: String,
    val latitude: Double,
    val longitude: Double,
    val locationName: String,
    val reminderTitle:String,

)

data class LocationDataForReminder(
    val id: String,
    val locationName: String,
    val reminderTitle:String,
)