package com.laivinieks.georemind.feature_reminder.presentation.add_edit_remainder.components.time

import android.app.AlarmManager
import android.icu.text.SimpleDateFormat
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.DialogWindowProvider
import androidx.core.content.getSystemService
import com.laivinieks.georemind.R
import java.util.Calendar
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerDialog(
    modifier: Modifier = Modifier,
    selectedHour: Int = 0,
    selectedMinute: Int = 0,
    selectedDate: Long = 0,
    callback: (
        showDialog: Boolean,
        selectedHour: Int,
        selectedMinute: Int,
        selectedDate: Long
    ) -> Unit

) {
    val dateFormat = SimpleDateFormat("dd.MM.yy")


    var isTimeSelection by remember {
        mutableStateOf(true)
    }

Log.d("selecteds","$selectedHour, $selectedDate")
    val timePickerState = rememberTimePickerState(
        initialHour = selectedHour,
        initialMinute = selectedMinute
    )
    val datePickerState = rememberDatePickerState(
        initialDisplayMode = DisplayMode.Picker,
        initialSelectedDateMillis = selectedDate,
        yearRange = IntRange(Calendar.getInstance()[Calendar.YEAR], 2100),

        )


    AlertDialog(
        properties = DialogProperties(usePlatformDefaultWidth = false),
        modifier = Modifier
            .fillMaxWidth(),
        onDismissRequest = { callback(false, timePickerState.hour, timePickerState.minute, datePickerState.selectedDateMillis!!) }
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            // Blurred background
            Box(
                modifier = Modifier
                    .background(Color.Black.copy(alpha = 0.3f))
                    .fillMaxSize()
                    .clickable {
                        callback(false, timePickerState.hour, timePickerState.minute, datePickerState.selectedDateMillis!!)
                    },
            )
            Column(
                modifier = Modifier

                    .background(
                        color = MaterialTheme.colorScheme.surface,
                        shape = RoundedCornerShape(size = 16.dp)
                    )
                    .clickable {

                    }
                    .padding(horizontal = 20.dp, vertical = 20.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Column(modifier = Modifier.padding(vertical = if (isTimeSelection) 58.dp else 0.dp)) {


                    // time picker
                    AnimatedVisibility(
                        visible = isTimeSelection,
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {

                            TimePicker(state = timePickerState)
                            TextButton(onClick = { isTimeSelection = !isTimeSelection }) {
                                Text(
                                    text = "${dateFormat.format(datePickerState.selectedDateMillis)}",
                                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold, fontSize = 32.sp)
                                )
                            }
                        }

                    }

                    // date picker
                    AnimatedVisibility(
                        visible = !isTimeSelection,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                            TextButton(onClick = { isTimeSelection = !isTimeSelection }) {
                                Text(
                                    text = "${timePickerState.hour}: ${timePickerState.minute}",
                                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold, fontSize = 32.sp)
                                )
                            }
                            DatePicker(
                                state = datePickerState,
                                modifier = Modifier.fillMaxWidth(),
                            )
                        }
                    }
                }
                // buttons
                Row(
                    modifier = Modifier
                        .padding(top = 12.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // dismiss button
                    TextButton(onClick = {
                        callback(
                            false,
                            timePickerState.hour,
                            timePickerState.minute,
                            datePickerState.selectedDateMillis!!
                        )
                    }) {
                        Text(
                            text = "Dismiss",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }

                    // confirm button
                    TextButton(
                        onClick = {

                            callback(false, timePickerState.hour, timePickerState.minute, datePickerState.selectedDateMillis!!)
                        }
                    ) {
                        Text(text = "Confirm", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                    }
                }

            }
        }

    }
}