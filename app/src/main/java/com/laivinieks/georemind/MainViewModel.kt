package com.laivinieks.georemind

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    // ViewModel to manage location permission state

    private val _askForLocationPermissions = mutableStateOf(false)
    val askForLocationPermissions: State<Boolean> = _askForLocationPermissions

    fun setAskForLocationPermissions(value: Boolean) {
        _askForLocationPermissions.value = value
    }


}