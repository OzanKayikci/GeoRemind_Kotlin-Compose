package com.laivinieks.georemind.feature_note.presentation.add_edit_note

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Save
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.laivinieks.georemind.feature_note.domain.util.Converters
import com.laivinieks.georemind.feature_note.presentation.add_edit_note.components.TransparentHintTextField
import com.laivinieks.georemind.ui.theme.LocalCustomColorsPalette
import com.laivinieks.georemind.ui.theme.iterateOverNoteColors
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun AddEditNoteScreen(
    navController: NavController,
    noteColor: Int,// this for if we selected existing note because in viewmodel we get random color and that it could be selected orange but background is blue after initialize the screen
    viewModel: AddEditNoteViewModel = hiltViewModel()
) {
    val titleState = viewModel.noteTitle.value
    val contentState = viewModel.noteContent.value

    val snackbarHostState = remember { SnackbarHostState() }

    val noteBackgroundAnimatable = remember {
        Animatable(
            Color(if (noteColor != -1) noteColor else viewModel.noteColor.value)
        )
    }

    val scope = rememberCoroutineScope()

    val noteColors = LocalCustomColorsPalette.current

    LaunchedEffect(key1 = true) {
        var iteratedNoteColor = iterateOverNoteColors(noteColors)
        val initColor = iteratedNoteColor.random()
        viewModel.updateNoteColor((iteratedNoteColor.indexOf(initColor)), iteratedNoteColor).also {
            noteBackgroundAnimatable.animateTo(
                targetValue = Color(initColor.toArgb()),
                animationSpec = tween(
                    durationMillis = 500
                ),
            )
        }

        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is AddEditNoteViewModel.UiEvent.ShowSnackBar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }

                is AddEditNoteViewModel.UiEvent.SaveNote -> {
                    navController.navigateUp()
                }
            }
        }

    }

    val colorRatio = if (isSystemInDarkTheme()) 0.3f else 0.5f
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.onEvent(AddEditNoteEvent.SaveNote)
            }, containerColor = MaterialTheme.colorScheme.primary) {
                Icon(imageVector = Icons.Rounded.Save, contentDescription = "Save Note")
            }
        },
    ) { paddingValues ->
        val padding = paddingValues
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp, vertical = paddingValues.calculateTopPadding() / 4)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                val colors = iterateOverNoteColors(noteColors)
                colors.forEach { color ->
                    val colorInt = color.toArgb()
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .shadow(15.dp, CircleShape)
                            .clip(CircleShape)
                            .background(color)
                            .border(
                                width = 3.dp,
                                color = if (viewModel.noteColor.value == colorInt) {
                                    MaterialTheme.colorScheme.onPrimaryContainer
                                } else Color.Transparent,
                                shape = CircleShape
                            )
                            .clickable {
                                scope.launch {
                                    noteBackgroundAnimatable.animateTo(
                                        targetValue = Color(colorInt),
                                        animationSpec = tween(
                                            durationMillis = 500
                                        ),
                                    )
                                }
                                viewModel.onEvent(AddEditNoteEvent.ChangeColor(colors.indexOf(color)))
                            }
                    ) {

                    }
                }
            }
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
                        viewModel.onEvent(AddEditNoteEvent.EnteredTitle(it))
                    }, onFocusChange = {
                        viewModel.onEvent(AddEditNoteEvent.ChangeTitleFocus(it))
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
                    viewModel.onEvent(AddEditNoteEvent.EnteredContent(it))
                }, onFocusChange = {
                    viewModel.onEvent(AddEditNoteEvent.ChangeContentFocus(it))
                },

                isHintVisible = contentState.isHintVisible,
                maxLine = 20,
                textStyle = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.onPrimaryContainer),
                modifier = Modifier
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(16.dp))
                    .background(Converters.getSecondaryColor(noteBackgroundAnimatable.value, colorRatio))
                    .padding(vertical = 24.dp, horizontal = 16.dp)
            )
        }
    }

}

