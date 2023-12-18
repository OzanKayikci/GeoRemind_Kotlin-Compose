package com.laivinieks.georemind.feature_reminder.presentation.add_edit_remainder

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector4D
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material.icons.rounded.Schedule
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
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
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.laivinieks.georemind.core.presentation.components.ColorPicker
import com.laivinieks.georemind.feature_note.presentation.add_edit_note.AddEditNoteEvent
import com.laivinieks.georemind.feature_reminder.presentation.add_edit_remainder.components.TimePickerDialog
import com.laivinieks.georemind.ui.theme.CustomColorsPalette
import com.laivinieks.georemind.ui.theme.iterateOverNoteColors
import kotlinx.coroutines.launch
import java.util.Calendar

@Composable
fun BottomSheetContent(
    modifier: Modifier = Modifier,
    iteratedNoteColors: List<Color>,
    selectedColor: Int,
    newColor: (Int) -> Unit,
) {

    var selectedHour by remember {
        mutableIntStateOf(Calendar.getInstance()[Calendar.HOUR_OF_DAY])
    }

    var selectedMinute by remember {
        mutableIntStateOf(Calendar.getInstance()[Calendar.MINUTE])
    }

    var showTimeDialog by remember {
        mutableStateOf(false)
    }

    var selectTime by remember { mutableStateOf(false) }
    var selectLocation by remember { mutableStateOf(false) }

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

        /** button for choosing time*/
        Box(
            modifier = if (selectTime) (Modifier
                .padding(0.dp, 8.dp)
                .shadow(16.dp, CircleShape)
                .clip(CircleShape)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)) else Modifier,
            contentAlignment = Alignment.Center
        )

        {
            Row(
                modifier = Modifier
                    .fillMaxWidth(if(selectTime) 0.78f else 0.92f)
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
               horizontalArrangement =  if(!selectTime) Arrangement.Center else Arrangement.Start
            ) {

                Switch(

                            checked = selectTime,
                    onCheckedChange = {
                        selectTime = it
                    },
                    thumbContent = if (selectTime) {
                        {
                            Icon(
                                imageVector = Icons.Rounded.Check,
                                contentDescription = null,
                                modifier = Modifier.size(SwitchDefaults.IconSize),
                                tint = MaterialTheme.colorScheme.primary,
                            )
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
                        if (!selectTime) "Choose Time" else "$selectedHour : $selectedMinute",
                        fontWeight = FontWeight.ExtraBold,
                        style = MaterialTheme.typography.titleLarge,

                    )
                }
            }
        }

        /** button for choosing location*/
        Box(
            modifier = if (selectLocation) (Modifier
                .padding(0.dp, 8.dp)
                .shadow(16.dp, CircleShape)
                .clip(CircleShape)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)) else Modifier,
            contentAlignment = Alignment.Center
        )

        {
            Row(
                modifier = Modifier
                    .fillMaxWidth(if(selectLocation) 0.78f else 1f)
                    .padding(16.dp),

                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement =  if(!selectLocation) Arrangement.Center else Arrangement.Start
            ) {

                Switch(

                    checked = selectLocation,
                    onCheckedChange = {
                        selectLocation = it
                    },
                    thumbContent = if (selectLocation) {
                        {
                            Icon(
                                imageVector = Icons.Filled.Check,
                                contentDescription = null,
                                modifier = Modifier.size(SwitchDefaults.IconSize),
                                tint = MaterialTheme.colorScheme.primary,
                            )
                        }
                    } else {
                        null
                    }
                )

                TextButton(
                    enabled = selectLocation,
                    onClick = {
                        // showDialog = true
                    },
                    modifier = Modifier
                ) {
                    Icon(
                        imageVector = Icons.Rounded.LocationOn,
                        contentDescription = null,
                        Modifier.size(30.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Choose Location",  fontWeight = FontWeight.ExtraBold, style = MaterialTheme.typography.titleLarge)
                }
            }
        }
        // Example button for choosing location

    }
}

//@Preview(showSystemUi = true)
//@Composable
//private fun bottomSheetPreview() {
//    BottomSheetContent()
//}