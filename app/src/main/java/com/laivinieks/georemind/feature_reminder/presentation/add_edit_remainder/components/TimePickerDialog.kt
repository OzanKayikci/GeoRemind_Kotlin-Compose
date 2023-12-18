package com.laivinieks.georemind.feature_reminder.presentation.add_edit_remainder.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerDialog(
    modifier: Modifier = Modifier,
    selectedHour: Int = 0,
    selectedMinute: Int = 0,

    callback: (
        showDialog: Boolean,
        selectedHour: Int,
        selectedMinute: Int
    ) -> Unit

) {

    var localSelectedHour by remember {
        mutableIntStateOf(selectedHour) // or use  mutableStateOf(0)
    }

    var localSelectedMinute by remember {
        mutableIntStateOf(selectedMinute) // or use  mutableStateOf(0)
    }
    val timePickerState = rememberTimePickerState(
        initialHour = localSelectedHour,
        initialMinute = localSelectedMinute
    )
    AlertDialog(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(size = 12.dp)
            ),
        onDismissRequest = { callback(false, localSelectedHour, localSelectedMinute) }
    ) {
        Column(
            modifier = Modifier
                .background(
                    color = Color.LightGray.copy(alpha = 0.3f)
                )
                .padding(top = 28.dp, start = 20.dp, end = 20.dp, bottom = 12.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // time picker
            TimePicker(state = timePickerState)


            // buttons
            Row(
                modifier = Modifier
                    .padding(top = 12.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                // dismiss button
                TextButton(onClick = { callback(false, localSelectedHour, localSelectedMinute) }) {
                    Text(text = "Dismiss")
                }

                // confirm button
                TextButton(
                    onClick = {

                        localSelectedHour = timePickerState.hour
                        localSelectedMinute = timePickerState.minute
                        callback(false, localSelectedHour, localSelectedMinute)
                    }
                ) {
                    Text(text = "Confirm")
                }
            }

        }
    }
}