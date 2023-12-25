package com.laivinieks.georemind.feature_reminder.presentation.add_edit_remainder.components

import com.laivinieks.georemind.feature_reminder.domain.model.LocationData

data class LocationSelectorState(
    val isSelected: Boolean,
    val location: LocationData? = null
)

