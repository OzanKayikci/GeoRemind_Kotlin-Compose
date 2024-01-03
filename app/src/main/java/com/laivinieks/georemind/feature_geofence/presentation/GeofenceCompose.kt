package com.laivinieks.georemind.feature_geofence.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.laivinieks.georemind.feature_geofence.presentation.GeofenceViewModel


@Composable
fun GeofenceCompose(
    geofenceViewModel: GeofenceViewModel = hiltViewModel()
) {
    LaunchedEffect(true) {
        geofenceViewModel.createGeofence()
    }
}