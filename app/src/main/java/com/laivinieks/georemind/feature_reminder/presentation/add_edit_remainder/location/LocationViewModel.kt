package com.laivinieks.georemind.feature_reminder.presentation.add_edit_remainder.location

import android.location.Location
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.laivinieks.georemind.feature_reminder.domain.use_case.location_use_case.LocationUserCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
  private val locationUserCases: LocationUserCases
) : ViewModel() {

    private var _location = mutableStateOf<Location?>(null)
    val location : MutableState<Location?> = _location

    private var getLocationJob: Job? = null

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
                _location.value = locationResult.lastLocation
//            for (location in locationResult.locations) {
//                // Handle location updates here
//            }
        }
    }



    fun startLocationUpdates() {

        viewModelScope.launch {
            locationUserCases.startLocationUpdatesUseCase(locationCallback)
        }
    }

    fun stopLocationUpdates() {
        viewModelScope.launch {
            locationUserCases.stopLocationUpdatesUseCase(locationCallback)
        }
    }
}