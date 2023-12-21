package com.laivinieks.georemind.feature_reminder.presentation.add_edit_remainder


import android.util.Log
import android.widget.Toast
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
import androidx.compose.runtime.rememberCoroutineScope

import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow

import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi


import com.laivinieks.georemind.core.presentation.components.ColorPicker
import com.laivinieks.georemind.feature_reminder.presentation.add_edit_remainder.location.LocationPickerDialog
import com.laivinieks.georemind.feature_reminder.presentation.add_edit_remainder.components.TimePickerDialog

import com.laivinieks.georemind.feature_reminder.presentation.add_edit_remainder.location.LaunchPermissionRequest
import kotlinx.coroutines.launch


import java.util.Calendar


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun BottomSheetContent(
    modifier: Modifier = Modifier,
    iteratedNoteColors: List<Color>,
    selectedColor: Int,
    newColor: (Int) -> Unit,
) {

    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    var selectedHour by remember {
        mutableIntStateOf(Calendar.getInstance()[Calendar.HOUR_OF_DAY])
    }

    var selectedMinute by remember {
        mutableIntStateOf(Calendar.getInstance()[Calendar.MINUTE])
    }

    var selectedLocationName by remember {
        mutableStateOf("Choose Location")
    }

    var showTimeDialog by remember {
        mutableStateOf(false)
    }
    var showLocationDialog by remember {
        mutableStateOf(false)
    }

    var selectTime by remember { mutableStateOf(false) }
    var selectLocation by remember { mutableStateOf(false) }

    var showLocationPermission by remember {
        mutableStateOf(false)
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
            }
        }

        // dialog fir location picker
        if (showLocationDialog) {
            LocationPickerDialog() { showDialog, locationName ->
                showLocationDialog = showDialog
                if (locationName.isNotBlank()) {
                    selectedLocationName = locationName

                } else {
                    selectLocation = false
                }

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
                    },
                    thumbContent = if (selectTime) {
                        {
//                            Icon(
//                                imageVector = Icons.Rounded.Check,
//                                contentDescription = null,
//                                modifier = Modifier.size(SwitchDefaults.IconSize),
//                                tint = MaterialTheme.colorScheme.primary,
//                            )
                        }
                    } else {
                        null
                    }
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
            modifier = Modifier,
            contentAlignment = Alignment.Center
        )

        {

            AnimatebleBox(state = selectLocation, modifier = Modifier.matchParentSize())
            Row(
                modifier = Modifier
                    .fillMaxWidth(if (selectLocation) 0.78f else 1f)
                    .padding(horizontal = 16.dp, vertical = 24.dp),

                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = if (!selectLocation) Arrangement.Center else Arrangement.Start
            ) {

                Switch(

                    checked = selectLocation,
                    onCheckedChange = {
                        selectLocation = !selectLocation
                        showLocationPermission = it
                    },
                    thumbContent = if (selectLocation) {
                        {
//                            Icon(
//                                imageVector = Icons.Filled.Check,
//                                contentDescription = null,
//                                modifier = Modifier.size(SwitchDefaults.IconSize),
//                                tint = MaterialTheme.colorScheme.primary,
//                            )
                        }
                    } else {
                        null
                    }
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
                    Text(selectedLocationName,maxLines = 1, fontWeight = FontWeight.ExtraBold, style = MaterialTheme.typography.titleLarge, overflow = TextOverflow.Ellipsis )
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