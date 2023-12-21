package com.laivinieks.georemind.feature_reminder.presentation.reminders

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.laivinieks.georemind.feature_note.domain.modal.Note
import com.laivinieks.georemind.feature_note.presentation.notes.NotesEvent
import com.laivinieks.georemind.feature_note.presentation.notes.NotesViewModel
import com.laivinieks.georemind.feature_note.presentation.notes.OrderSection
import com.laivinieks.georemind.feature_reminder.presentation.util.ReminderFeatureScreen

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RemindersScreen(
    navController: NavController,
    viewModel: NotesViewModel = hiltViewModel()

) {
    val remainders = listOf(
        Note(
            id = 1,
            title = "Title",
            content = " asdf asdf as sf jksdfslfkash jjasdghkhjgfashdfjsg kash jContent sdf sd.fl gjksdşf lkdfkdg lkddjk  klj dfhgflkg jhslkfjdg sdfjkh gsdf kj",
            timestamp = 0,
            color = 1
        ),
        Note(id = 2, title = "Title", content = "Content", timestamp = 0, color = 2),
        Note(id = 3, title = "Title", content = "Content", timestamp = 0, color = 3),
        Note(id = 4, title = "Title", content = "asdfgdsfgsdfg sddsdg", timestamp = 0, color = 2),
        Note(
            id = 5,
            title = "Title",
            content = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s",
            timestamp = 0,
            color = 4
        ),
        Note(id = 6, title = "Title", content = "Content", timestamp = 0, color = 5)

    )


    val state = viewModel.state.value
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = {
            //SnackbarHost(hostState = snackbarHostState)
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
                        viewModel.onEvent(NotesEvent.ToggleOrderSection)
                    }
                ) {
                    Icon(imageVector = Icons.Rounded.KeyboardArrowDown, contentDescription = "sort")
                }
            }
            AnimatedVisibility(
                visible = state.isOrderSectionVisible,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {
                OrderSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    noteOrder = state.noteOrder
                ) {
                    viewModel.onEvent(NotesEvent.Order(it))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Adaptive(150.dp),
                modifier = Modifier
                    .fillMaxSize()


            ) {
                items(remainders) { remainder ->

                    ReminderItem(
                        note = remainder,
                        modifier = Modifier
                            .padding(8.dp)
                            .animateItemPlacement(
                                animationSpec = spring(
                                    dampingRatio = Spring.DampingRatioMediumBouncy,
                                    stiffness = Spring.StiffnessMedium / 4,
                                    visibilityThreshold = IntOffset.VisibilityThreshold,

                                    )
                                //  tween(durationMillis = 500, delayMillis = 100, easing = FastOutLinearInEasing)
                            ),
                        onDeleteClick = {}
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}