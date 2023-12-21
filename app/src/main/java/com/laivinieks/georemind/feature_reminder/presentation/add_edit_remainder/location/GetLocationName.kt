package com.laivinieks.georemind.feature_reminder.presentation.add_edit_remainder.location

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.os.Build
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import java.util.Locale


fun getLocationName(
    context: Context,
    latitude: Double,
    longitude: Double,
    onNameFetched: (Boolean, String) -> Unit
) {


    val geocoder = Geocoder(context, Locale.getDefault())
//Fetch address from location
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        geocoder.getFromLocation(latitude, longitude, 1, object : Geocoder.GeocodeListener {
            override fun onGeocode(addresses: MutableList<Address>) {
                Log.w("locatin", addresses.toString())
                onNameFetched(true, addresses[0].getAddressLine(0))
            }

            override fun onError(errorMessage: String?) {
                super.onError(errorMessage)
                onNameFetched(false, "$latitude - $longitude")

            }

        })
    } else {
        try {
            geocoder.getFromLocation(latitude, longitude, 1)?.let { addresses ->
                onNameFetched(true, addresses[0].featureName)
            } ?: onNameFetched(false, "$latitude - $longitude")


        } catch (e: Exception) {
            onNameFetched(false, "$latitude - $longitude")

        }


    }
}