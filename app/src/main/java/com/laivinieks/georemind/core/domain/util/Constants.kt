package com.laivinieks.georemind.core.domain.util

import androidx.datastore.preferences.core.booleanPreferencesKey
import com.laivinieks.georemind.ui.theme.*

object Constants {


    const val UNDO_SNACKBAR = "UNDO_SNACKBAR"
    const val DATA_STORE_NAME = "DataStore"

    const val LOCATION_REQUEST_INTERVAL = 5000L
    const val LOCATION_MINIMAL_DISTANCE = 10.0F

    const val REQUEST_CHECK_SETTINGS = 123

    val KEY_PERMISSION_LOCATION = booleanPreferencesKey("locationPermission")
    val KEY_PERMISSION_NOTIFICATION = booleanPreferencesKey("notificationPermission")

    const val LOCATION_NOTIFICATION_ID = "LOCATION"

    const val GPS_DISABLED = "GPS_DISABLED"
    const val NETWORK_DISABLED = "NETWORK_DISABLED"
    const val PERMISSIONS_MISSING = "PERMISSIONS_MISSING"

}