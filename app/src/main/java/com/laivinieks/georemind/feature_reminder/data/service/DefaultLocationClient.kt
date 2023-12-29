package com.laivinieks.georemind.feature_reminder.data.service

import android.annotation.SuppressLint
import android.content.Context

import android.location.Location
import android.location.LocationManager
import android.os.Looper
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.Status

import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Granularity
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.Priority
import com.laivinieks.georemind.core.domain.util.Constants
import com.laivinieks.georemind.feature_reminder.domain.service.LocationClient
import com.laivinieks.georemind.feature_reminder.domain.util.hasLocationPermission
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch

class DefaultLocationClient(
    private val context: Context,
    private val client: FusedLocationProviderClient
) : LocationClient {

    private fun createRequest(interval: Long): LocationRequest =
        // New builder
        LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, interval).apply {
            setMinUpdateDistanceMeters(Constants.LOCATION_MINIMAL_DISTANCE)
            setGranularity(Granularity.GRANULARITY_PERMISSION_LEVEL)
            setWaitForAccurateLocation(true)
        }.build()

    private lateinit var locationCallback: LocationCallback

    @SuppressLint("MissingPermission")
    override fun getLocationUpdates(interval: Long, locationRequest: (LocationRequest) -> Unit): Flow<Location> {
        return callbackFlow {
            val request = createRequest(interval)


            locationCallback = object : LocationCallback() {
                override fun onLocationResult(result: LocationResult) {
                    super.onLocationResult(result)
                    result.locations.lastOrNull()?.let { location ->
                        launch { send(location) }
                    } ?: launch { result.lastLocation?.let { send(it) } }
                }
            }

            if (!context.hasLocationPermission()) {

                throw LocationClient.LocationException(Constants.PERMISSIONS_MISSING)
            }

            val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
            val isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

            if (!isGpsEnabled && !isNetworkEnabled) {
                locationRequest(request)
                //  throw LocationClient.LocationException(Constants.GPS_DISABLED,Status(CommonStatusCodes.RESOLUTION_REQUIRED))
            }



            client.requestLocationUpdates(
                request,
                locationCallback,
                Looper.getMainLooper()
            )

            awaitClose {
                client.removeLocationUpdates(locationCallback)
            }
        }


    }

    override fun stopLocationTracking() {
        client.flushLocations()
        client.removeLocationUpdates(locationCallback)
    }
}