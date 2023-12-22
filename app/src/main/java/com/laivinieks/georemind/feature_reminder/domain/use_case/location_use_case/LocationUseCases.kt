package com.laivinieks.georemind.feature_reminder.domain.use_case.location_use_case


/**
 * we did it because there  can be many use cases. and therefore our Viewmodel constructor will be very long.
 * that is why we create a data class. we will just take this data class as parameter in constructor
 * */
data class LocationUseCases(
    val startLocationUpdatesUseCase: StartLocationUpdatesUseCase,
    val stopLocationUpdatesUseCase: StopLocationUpdatesUseCase,
    val checkLocationSettingsUseCase: CheckLocationSettingsUseCase
)
