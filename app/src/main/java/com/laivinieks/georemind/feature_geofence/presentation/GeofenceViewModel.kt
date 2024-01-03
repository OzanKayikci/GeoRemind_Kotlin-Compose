package com.laivinieks.georemind.feature_geofence.presentation

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.Geofence
import com.laivinieks.georemind.feature_geofence.domain.model.LocationGeofenceData
import com.laivinieks.georemind.feature_geofence.domain.use_case.GeofenceUseCases
import com.laivinieks.georemind.feature_geofence.domain.use_case.GetAllLocationsUseCase

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GeofenceViewModel @Inject constructor(
    private val geofenceUseCases: GeofenceUseCases,
    private val getAllLocationsUseCase: GetAllLocationsUseCase
) : ViewModel() {


    private var _locations = mutableStateOf<List<LocationGeofenceData>>(emptyList())
    val locations: MutableState<List<LocationGeofenceData>> = _locations


    private var getLocationsJob: Job? = null

    init {
        getLocations()
    }

    private fun getLocations() {
        getLocationsJob?.cancel()
        getLocationsJob = getAllLocationsUseCase()
            .onEach { savedLocations ->

                var geofenceLocations: List<LocationGeofenceData> = emptyList()

                savedLocations.forEach { reminder ->
                    val locationData = reminder.location ?: return@forEach

                    Log.d("locationData", locationData.latitude.toString() + locationData.locationName)
                    geofenceLocations = geofenceLocations.plus(
                        LocationGeofenceData(
                            //TODO: take reminder id as this id
                            id = reminder.id.toString(),
                            latitude = locationData.latitude,
                            longitude = locationData.longitude,
                            locationName = locationData.locationName,
                            reminderTitle = reminder.title
                        )
                    )
                }
                Log.d("lcoaitns", geofenceLocations.toString())
                _locations.value = geofenceLocations

            }.launchIn(viewModelScope)
    }

    fun createGeofence() {
        removeGeofence()
        locations.value.map { locationData ->
            geofenceUseCases.createGeofenceUseCase(locationData, 50f)
        }.also { geofenceList ->
            addGeofence(geofenceList)
        }

    }

    private fun addGeofence(geofenceList: List<Geofence>) {
        Log.d("addGeofence", geofenceList.toString())
        viewModelScope.launch {
            geofenceUseCases.addGeofenceUseCase(geofenceList, onSuccess = {
                Log.d("geofence", "Added success")
            }, onFailure = {
                Log.d("geofence", "Added fail")

            })

        }
    }

    fun removeGeofence() {

        viewModelScope.launch {
            geofenceUseCases.removeGeofenceUseCase()
        }
    }
}