@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.laivinieks.georemind.feature_reminder.presentation.add_edit_remainder

import android.content.Intent
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material.icons.rounded.Save
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.laivinieks.georemind.core.domain.util.Converters
import com.laivinieks.georemind.core.presentation.components.TransparentHintTextField
import com.laivinieks.georemind.feature_note.presentation.add_edit_note.AddEditNoteEvent
import com.laivinieks.georemind.feature_note.presentation.add_edit_note.AddEditNoteViewModel
import com.laivinieks.georemind.feature_reminder.data.service.LocationService
import com.laivinieks.georemind.feature_reminder.presentation.add_edit_remainder.components.NotificationPermissionRequest
import com.laivinieks.georemind.ui.theme.LocalCustomColorsPalette
import com.laivinieks.georemind.ui.theme.iterateOverNoteColors
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditReminderScreen(
    navController: NavController,
    reminderColor: Int,
    viewModel: AddEditReminderViewModel = hiltViewModel()
) {

    val context = LocalContext.current

    val titleState = viewModel.reminderTitle.value
    val contentState = viewModel.reminderContent.value
    val locationState = viewModel.reminderLocation.value
    val reminderTimeState = viewModel.reminderTimestamp.value
    val selectedColor = viewModel.reminderColor.value

    val scope = rememberCoroutineScope()

    val sheetState = rememberModalBottomSheetState()

    var showBottomSheet by remember { mutableStateOf(false) }
    var showNotificationRequest by remember { mutableStateOf(false) }

    val snackbarHostState = remember { SnackbarHostState() }


    val iteratedReminderColor = iterateOverNoteColors(LocalCustomColorsPalette.current)

    val noteBackgroundAnimatable = remember {
        Animatable(
            Color(if (reminderColor != -1) iteratedReminderColor[reminderColor].toArgb() else iteratedReminderColor[selectedColor].toArgb())
        )
    }

    if (showNotificationRequest) {
        NotificationPermissionRequest() { isGranted ->
            showBottomSheet = isGranted
            showNotificationRequest = false
        }
    }

    LaunchedEffect(key1 = true) {

//        val initColor = iteratedReminderColor.random()
//
//        viewModel.updateReminderColor((iteratedReminderColor.indexOf(initColor)), iteratedReminderColor).also {
//            noteBackgroundAnimatable.animateTo(
//                targetValue = Color(initColor.toArgb()),
//                animationSpec = tween(
//                    durationMillis = 500
//                ),
//            )
//        }


        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is AddEditReminderViewModel.UiEvent.ShowSnackBar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }

                is AddEditReminderViewModel.UiEvent.SaveNote -> {
                    navController.navigateUp()
                }
            }
        }

    }
    val colorRatio = if (isSystemInDarkTheme()) 0.3f else 0.5f
    Scaffold(

        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.onEvent(AddEditReminderEvent.SaveReminder)
            }, containerColor = MaterialTheme.colorScheme.primary) {
                Icon(imageVector = Icons.Rounded.Save, contentDescription = "Save Reminder")
            }
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
    ) { paddingValues ->
        val padding = paddingValues
        Box(modifier = Modifier, contentAlignment = Alignment.BottomCenter) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 8.dp, vertical = paddingValues.calculateTopPadding() / 4)
            ) {

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(Converters.getSecondaryColor(noteBackgroundAnimatable.value, colorRatio))
                        .padding(vertical = 16.dp, horizontal = 16.dp)
                ) {
                    //title
                    TransparentHintTextField(
                        text = titleState.text,
                        hint = titleState.hint,
                        onValueChange = {
                            viewModel.onEvent(AddEditReminderEvent.EnteredTitle(it))
                        }, onFocusChange = {
                            viewModel.onEvent(AddEditReminderEvent.ChangeTitleFocus(it))
                        },
                        isHintVisible = titleState.isHintVisible,
                        maxLine = 1,
                        textStyle = MaterialTheme.typography.titleLarge.copy(color = MaterialTheme.colorScheme.onPrimaryContainer),
                        modifier = Modifier.weight(8f)
                    )

                    Box(
                        modifier = Modifier

                            .size(30.dp)
                            .clip(RoundedCornerShape(50))
                            .background(noteBackgroundAnimatable.value)
                            .weight(1f)
                    )

                }

                Spacer(modifier = Modifier.height(24.dp))
                //content
                TransparentHintTextField(
                    text = contentState.text,
                    hint = contentState.hint,
                    onValueChange = {
                        viewModel.onEvent(AddEditReminderEvent.EnteredContent(it))
                    }, onFocusChange = {
                        viewModel.onEvent(AddEditReminderEvent.ChangeContentFocus(it))
                    },

                    isHintVisible = contentState.isHintVisible,
                    maxLine = 20,
                    textStyle = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.onPrimaryContainer),
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(16.dp))
                        .background(Converters.getSecondaryColor(noteBackgroundAnimatable.value, colorRatio))
                        .padding(vertical = 24.dp, horizontal = 16.dp)
                )

                // modal bottom sheet
                if (showBottomSheet) {
                    ModalBottomSheet(
                        onDismissRequest = {
                            showBottomSheet = false
                        },
                        sheetState = sheetState
                    ) {
                        // Sheet content

                        BottomSheetContent(
                            modifier = Modifier.fillMaxWidth(),
                            iteratedNoteColors = iteratedReminderColor,
                            selectedColor = selectedColor,
                            locationState = locationState,
                            reminderTimeState = reminderTimeState
                        ) { newColor, newLocation, newTime ->

                            newColor?.let { color ->
                                if (color != selectedColor) {
                                    viewModel.onEvent(AddEditReminderEvent.ChangeColor(color))
                                    scope.launch {
                                        noteBackgroundAnimatable.animateTo(
                                            targetValue = iteratedReminderColor[color],
                                            animationSpec = tween(
                                                durationMillis = 500
                                            ),
                                        )
                                    }
                                }
                            }

                            newLocation?.let { locationState ->
                                if (locationState != viewModel.reminderLocation.value) {
                                    viewModel.onEvent(AddEditReminderEvent.ChangeLocationSelection(locationState.isSelected))
                                    viewModel.onEvent(AddEditReminderEvent.ChangeLocation(locationState.location))
                                }
                            }
                            newTime?.let { timeState ->
                                if (timeState != viewModel.reminderTimestamp.value) {
                                    viewModel.onEvent(AddEditReminderEvent.ChangeTimeSelection(timeState.isSelected))
                                    viewModel.onEvent(
                                        AddEditReminderEvent.ChangeReminderTime(
                                            Triple(
                                                timeState.hour!!,
                                                timeState.minute!!,
                                                timeState.date!!
                                            )
                                        )
                                    )

                                }
                            }

                        }


                    }
                }
            }

            //Bottom Sheet Button
            Button(
                colors = ButtonDefaults.buttonColors(
                    Color.Transparent,
                    contentColor = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier
                    .offset(y = (-10).dp)
                    .size(250.dp, 50.dp),

                onClick = { showNotificationRequest = true }
            ) {
                Icon(
                    imageVector = Icons.Rounded.KeyboardArrowUp,
                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                    contentDescription = "bottom sheet button",
                    modifier = Modifier.size(60.dp)
                )
            }
        }

    }


}