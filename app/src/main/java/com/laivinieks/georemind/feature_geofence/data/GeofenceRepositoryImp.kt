package com.laivinieks.georemind.feature_geofence.data


import com.google.android.gms.location.Geofence

import com.google.android.gms.tasks.Task
import com.google.gson.Gson
import com.laivinieks.georemind.feature_geofence.domain.model.LocationDataForReminder
import com.laivinieks.georemind.feature_geofence.domain.repository.GeofenceRepository
import com.laivinieks.georemind.feature_geofence.domain.model.LocationGeofenceData


import javax.inject.Inject

class GeofenceRepositoryImp @Inject constructor(
    private val geofenceManager: GeofenceManager
) : GeofenceRepository {
    override fun createGeofence(locationData: LocationGeofenceData, radius: Float): Geofence {

        val reminderData = LocationDataForReminder(
            id = locationData.id, locationName = locationData.locationName.take(20), reminderTitle = locationData.reminderTitle.take(20)

        )
        val requestId = Gson().toJson(reminderData)
        return Geofence.Builder()
            .setRequestId(requestId)
            .setCircularRegion(locationData.latitude, locationData.longitude, radius)
            .setExpirationDuration(Geofence.NEVER_EXPIRE)
            .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
            .build()
    }

    override suspend fun addGeofence(geofenceList: List<Geofence>, onSuccess: () -> Unit, onFailure: () -> Unit): Task<Void>? {
        return geofenceManager.addGeofence(geofenceList, onSuccess, onFailure)
    }

    override suspend fun removeGeofenceUseCase() {
        return geofenceManager.removeGeofence()
    }


}

