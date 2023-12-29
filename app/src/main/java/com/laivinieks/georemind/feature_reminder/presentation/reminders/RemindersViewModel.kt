package com.laivinieks.georemind.feature_reminder.presentation.reminders

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.laivinieks.georemind.core.domain.util.OrderType
import com.laivinieks.georemind.feature_note.domain.modal.Note
import com.laivinieks.georemind.feature_note.domain.modal.NoteState
import com.laivinieks.georemind.feature_note.domain.util.NoteOrder
import com.laivinieks.georemind.feature_note.presentation.notes.NotesEvent
import com.laivinieks.georemind.feature_reminder.domain.model.Reminder
import com.laivinieks.georemind.feature_reminder.domain.model.ReminderState
import com.laivinieks.georemind.feature_reminder.domain.use_case.ReminderUseCases
import com.laivinieks.georemind.feature_reminder.domain.util.ReminderOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RemindersViewModel @Inject constructor(
    private val reminderUseCases: ReminderUseCases
) : ViewModel() {

    private var _state = mutableStateOf(ReminderState())
    val state: MutableState<ReminderState> = _state

    private var recentlyDeletedReminder: Reminder? = null
    private var getRemindersJob: Job? = null


    init {

        getReminders(ReminderOrder.Date(OrderType.Descending))
    }


    fun onEvent(event: RemindersEvent) {
        when (event) {
            is RemindersEvent.Order -> {

                /**if user select already selected*/
                /**
                 * if we don't use ::class it will check referential equality - but their references different
                 * because we defined NoteOrders normal class not data class
                 * because of that we check if their class are the same
                 * */
                if (state.value!!.reminderOrder::class == event.reminderOrder::class && state.value!!.reminderOrder.orderType == event.reminderOrder.orderType) {
                    return
                }
                getReminders(event.reminderOrder)
            }

            is RemindersEvent.DeleteReminder -> {
                viewModelScope.launch {
                    reminderUseCases.deleteReminder(event.reminder)
                    recentlyDeletedReminder = event.reminder
                }
            }

            is RemindersEvent.RestoreReminder -> {
                viewModelScope.launch {
                    reminderUseCases.addReminder(recentlyDeletedReminder ?: return@launch)
                    recentlyDeletedReminder = null
                }
            }

            is RemindersEvent.ToggleOrderSection -> {
                _state.value = state.value!!.copy(
                    isOrderSectionVisible = !state.value!!.isOrderSectionVisible
                )
            }

        }
    }


    private fun getReminders(reminderOrder: ReminderOrder) {
        getRemindersJob?.cancel()
        getRemindersJob = reminderUseCases.getReminders(reminderOrder)
            .onEach { reminders ->

                _state.value = state.value!!.copy(
                    reminders = reminders,
                    reminderOrder = reminderOrder,
                )
            }.launchIn(viewModelScope)
    }
}