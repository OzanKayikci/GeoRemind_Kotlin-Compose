package com.laivinieks.georemind.feature_reminder.presentation.add_edit_remainder


import android.location.Location
import android.util.Log
import androidx.compose.animation.AnimatedVisibility

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons

import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material.icons.rounded.Schedule

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch

import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color


import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow

import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.android.gms.maps.model.LatLng


import com.laivinieks.georemind.core.presentation.components.ColorPicker
import com.laivinieks.georemind.feature_reminder.domain.model.LocationData
import com.laivinieks.georemind.feature_reminder.presentation.add_edit_remainder.components.LocationSelectorState
import com.laivinieks.georemind.feature_reminder.presentation.add_edit_remainder.components.ReminderTimeSelectorState
import com.laivinieks.georemind.feature_reminder.presentation.add_edit_remainder.location.LocationPickerDialog
import com.laivinieks.georemind.feature_reminder.presentation.add_edit_remainder.components.TimePickerDialog

import com.laivinieks.georemind.feature_reminder.presentation.add_edit_remainder.location.LaunchPermissionRequest


import java.util.Calendar


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun BottomSheetContent(
    modifier: Modifier = Modifier,
    iteratedNoteColors: List<Color>,
    selectedColor: Int,
    locationState: LocationSelectorState,
    reminderTimeState: ReminderTimeSelectorState,
    newValues: (newColor: Int?, newLocation: LocationSelectorState?, newTime: ReminderTimeSelectorState?) -> Unit,
) {


    var selectedHour by remember {
        mutableIntStateOf(reminderTimeState.hour ?: Calendar.getInstance()[Calendar.HOUR_OF_DAY])
    }

    var selectedMinute by remember {
        mutableIntStateOf(reminderTimeState.minute ?: Calendar.getInstance()[Calendar.MINUTE])
    }

    var selectedLocation by remember {
        mutableStateOf(locationState.location)
    }
    var selectedLocationName by remember {
        mutableStateOf(locationState.location?.let { it.locationName } ?: "Choose Location")
    }

    var showTimeDialog by remember {
        mutableStateOf(false)
    }
    var showLocationDialog by remember {
        mutableStateOf(false)
    }

    var selectTime by remember { mutableStateOf(reminderTimeState.isSelected) }
    var selectLocation by remember { mutableStateOf(locationState.isSelected) }

    var showLocationPermission by remember {
        mutableStateOf(false)
    }


    var newColor: (Int) -> Unit = {
        newValues(it, null, null)
    }

    Column(
        modifier = modifier
            .padding(16.dp),

        ) {

        ColorPicker(
            noteColors = iteratedNoteColors,
            selectedColor = selectedColor,
            newColor = newColor,
            modifier = Modifier.padding(10.dp)
        )

//location permissions dialog
        if (showLocationPermission) {
            LaunchPermissionRequest() { isGranted ->
                if (isGranted) {
                    selectLocation = true

                    showLocationDialog = true
                } else {
                    selectLocation = false
                }
                showLocationPermission = false
            }
        }

        // dialog fir time picker
        if (showTimeDialog) {
            TimePickerDialog(
                selectedHour = selectedHour,
                selectedMinute = selectedMinute
            ) { lshowDialog, lselectedHour, lselectedMinute ->
                showTimeDialog = lshowDialog
                selectedHour = lselectedHour
                selectedMinute = lselectedMinute

                val timeState = ReminderTimeSelectorState(selectTime, selectedHour, selectedMinute)
                newValues(null, null, timeState)
            }
        }

        // dialog for location picker
        if (showLocationDialog) {
            LocationPickerDialog(selectedLocation) { showDialog, locationData ->
                showLocationDialog = showDialog
                if (locationData != null) {
                    if (locationData.locationName.isNotBlank()) {
                        selectedLocationName = locationData.locationName
                        selectedLocation = locationData

                    } else {
                        selectLocation = false
                    }
                } else {
                    selectLocation = false
                }
                val locationState = LocationSelectorState(selectLocation, locationData)
                newValues(null, locationState, null)

            }
        }

        /** button for choosing time*/
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {


            AnimatebleBox(modifier = Modifier.matchParentSize(), selectTime)

            Row(
                modifier = Modifier
                    .fillMaxWidth(0.78f)
                    .padding(horizontal = 16.dp, vertical = 24.dp),

                horizontalArrangement = Arrangement.Start
            ) {

                Switch(
                    checked = selectTime,
                    onCheckedChange = {
                        selectTime = it
                        val timeState = ReminderTimeSelectorState(it, selectedHour, selectedMinute)
                        newValues(null, null, timeState)
                    },

                    )

                TextButton(
                    enabled = selectTime,
                    onClick = {
                        showTimeDialog = true
                    },
                    modifier = Modifier
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Schedule,
                        contentDescription = null,
                        Modifier.size(30.dp),

                        )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        if (!selectTime) "Choose Time" else String.format("%02d:%02d", selectedHour, selectedMinute),
                        fontWeight = FontWeight.ExtraBold,
                        style = MaterialTheme.typography.titleLarge,

                        )
                }
            }
        }

        /** button for choosing location*/
        Box(
            modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center
        )

        {

            AnimatebleBox(state = selectLocation, modifier = Modifier.matchParentSize())
            Row(
                modifier = Modifier
                    .fillMaxWidth(0.78f)
                    .padding(horizontal = 16.dp, vertical = 24.dp),

                horizontalArrangement = Arrangement.Start
            ) {

                Switch(

                    checked = selectLocation,
                    onCheckedChange = {
                        selectLocation = !selectLocation
                        showLocationPermission = it

                        val locationState = LocationSelectorState(selectLocation, selectedLocation)
                        newValues(null, locationState, null)
                    },

                    )

                TextButton(
                    enabled = selectLocation,
                    onClick = {
                        showLocationDialog = true
                    },
                    modifier = Modifier,
                ) {
                    Icon(
                        imageVector = Icons.Rounded.LocationOn,
                        contentDescription = null,
                        Modifier.size(30.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        selectedLocationName,
                        maxLines = 1,
                        fontWeight = FontWeight.ExtraBold,
                        style = MaterialTheme.typography.titleLarge,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }

    }


}


@Composable
fun AnimatebleBox(modifier: Modifier = Modifier, state: Boolean) {

    AnimatedVisibility(
        visible = state,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Box(
            modifier = modifier
                .padding(0.dp, 8.dp)
                .shadow(4.dp, CircleShape)
                .clip(CircleShape)
                .fillMaxWidth()
                .height(80.dp)
                .background(MaterialTheme.colorScheme.background)
        )
    }
}


//@Preview(showSystemUi = true)
//@Composable
//private fun bottomSheetPreview() {
//    BottomSheetContent()
//}