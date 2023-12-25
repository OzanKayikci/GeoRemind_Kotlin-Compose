package com.laivinieks.georemind.feature_reminder.presentation.reminders

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.laivinieks.georemind.R
import com.laivinieks.georemind.feature_note.presentation.notes.NotesEvent

import com.laivinieks.georemind.feature_reminder.presentation.reminders.OrderSection
import com.laivinieks.georemind.feature_reminder.presentation.util.ReminderFeatureScreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RemindersScreen(
    navController: NavController,
    viewModel: RemindersViewModel = hiltViewModel()

) {


    val state = viewModel.state.value
    val scope = rememberCoroutineScope()
    val itemVisibility = remember {
        Animatable(1f)
    }
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(ReminderFeatureScreen.AddEditReminderScreen.route)
                },
                containerColor = MaterialTheme.colorScheme.primary

            ) {
                Icon(painter = painterResource(id = R.drawable.ic_bottom_location_add_focused), contentDescription = "Add Remainder")
            }
        }
    ) { innerPadding ->
        val padding = innerPadding
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(0.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp), horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Remainders",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                IconButton(
                    onClick = {
                        viewModel.onEvent(RemindersEvent.ToggleOrderSection)
                    }
                ) {
                    Icon(imageVector = Icons.Rounded.KeyboardArrowDown, contentDescription = "sort")
                }
            }
            AnimatedVisibility(
                visible = state.isOrderSectionVisible,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically(),
            ) {
                OrderSection(
                    modifier = Modifier
                        .fillMaxWidth(),
                    reminderOrder = state.reminderOrder
                ) {
                    viewModel.onEvent(RemindersEvent.Order(it))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Adaptive(150.dp),
                modifier = Modifier
                    .fillMaxSize()


            ) {
                items(items = state.reminders, key = { it.id!! }) { reminder ->

                    ReminderItem(
                        reminder = reminder,
                        modifier = Modifier
                            .padding(8.dp)
                            .animateItemPlacement(
                                animationSpec = spring(
                                    dampingRatio = Spring.DampingRatioMediumBouncy,
                                    stiffness = Spring.StiffnessMedium / 4,
                                    visibilityThreshold = IntOffset.VisibilityThreshold,

                                    )
                                //  tween(durationMillis = 500, delayMillis = 100, easing = FastOutLinearInEasing)
                            )
                            .clickable {
                                navController.navigate(ReminderFeatureScreen.AddEditReminderScreen.route + "?reminderId=${reminder.id}&reminderColor=${reminder.color}")
                            },
                        onDeleteClick = {
                            scope.launch {
                                itemVisibility.animateTo(targetValue = 0f, animationSpec = tween(2000))

                            }
                            viewModel.onEvent(RemindersEvent.DeleteReminder(reminder = reminder))
                            scope.launch {
                                val result = snackbarHostState.showSnackbar(message = "Note Deleted", actionLabel = "Undo")

                                if (result == SnackbarResult.ActionPerformed) {
                                    viewModel.onEvent(RemindersEvent.RestoreReminder)
                                }
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}