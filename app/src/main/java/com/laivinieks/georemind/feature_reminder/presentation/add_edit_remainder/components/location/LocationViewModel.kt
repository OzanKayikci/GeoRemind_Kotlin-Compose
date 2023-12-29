package com.laivinieks.georemind.feature_reminder.presentation.add_edit_remainder.components.location

import android.app.Activity
import android.content.Context
import android.content.IntentSender
import android.location.Location
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.laivinieks.georemind.core.domain.util.Constants
import com.laivinieks.georemind.feature_reminder.domain.use_case.location_use_case.LocationUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    private val locationUseCases: LocationUseCases
) : ViewModel() {

    private var _location = mutableStateOf<Location?>(null)
    val location: MutableState<Location?> = _location

//    private val locationCallback = object : LocationCallback() {
//        override fun onLocationResult(locationResult: LocationResult) {
//            _location.value = locationResult.lastLocation
//
//        }
//    }


    fun startLocationUpdates(context: Context) {

        locationUseCases.startLocationUpdatesUseCase(
            interval = Constants.LOCATION_REQUEST_INTERVAL,
            scope = viewModelScope,

            onFailure = { request ->
                if (request != null) {
                    viewModelScope.launch {
                        val locationSettingsBuilder = LocationSettingsRequest.Builder().addLocationRequest(request)
                        val locationSettingsClient = LocationServices.getSettingsClient(context)
                        val task = locationSettingsClient.checkLocationSettings(locationSettingsBuilder.build())
                        task.addOnFailureListener { exception ->
                            if (exception is ResolvableApiException) {
                                Log.d("exeption true", exception.toString())
                                Toast.makeText(context, "Location settings are not satisfied. Please open location", Toast.LENGTH_LONG).show()

                                exception.startResolutionForResult(context as Activity, Constants.REQUEST_CHECK_SETTINGS)

                            }
                        }

                    }
                }
            },
            onSuccess = { locationResult ->
                _location.value = locationResult
            }
        )
    }

//    fun checkLocationSettings(activity: Activity, isEnabled: (Boolean) -> Unit) {
//        viewModelScope.launch {
//            locationUseCases.checkLocationSettingsUseCase(
//                onSuccess = {
//                    isEnabled(true)
//                },
//                onFailure = { exception ->
//                    if (exception is ResolvableApiException) {
//                        isEnabled(false)
//                        // Location settings are not satisfied, but can be fixed by showing the user a dialog.
//                        try {
//                            // Show the dialog by calling startResolutionForResult(),
//                            // and check the result in onActivityResult().
//
//                            exception.startResolutionForResult(activity, Constants.REQUEST_CHECK_SETTINGS)
//
//                        } catch (sendEx: IntentSender.SendIntentException) {
//                            // Ignore the error.
//
//                        }
//                    } else {
//                        // Location settings are not satisfied and cannot be fixed.
//                        isEnabled(false)
//                    }
//
//                }
//            )
//        }
//    }

    fun stopLocationUpdates() {
        viewModelScope.launch {
            locationUseCases.stopLocationUpdatesUseCase()
        }
    }
}