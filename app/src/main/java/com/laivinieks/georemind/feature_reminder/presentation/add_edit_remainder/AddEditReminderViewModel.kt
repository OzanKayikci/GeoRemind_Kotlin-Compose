package com.laivinieks.georemind.feature_reminder.presentation.add_edit_remainder

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.laivinieks.georemind.core.domain.util.TimeConverters.convertTimeToTimestamp
import com.laivinieks.georemind.core.domain.util.TimeConverters.convertTimestampToTime
import com.laivinieks.georemind.core.presentation.components.NoteTextFieldState
import com.laivinieks.georemind.feature_note.domain.modal.InvalidNoteException
import com.laivinieks.georemind.feature_reminder.domain.model.Reminder
import com.laivinieks.georemind.feature_reminder.domain.use_case.ReminderUseCases
import com.laivinieks.georemind.feature_reminder.presentation.add_edit_remainder.components.location.LocationSelectorState
import com.laivinieks.georemind.feature_reminder.presentation.add_edit_remainder.components.time.ReminderTimeSelectorState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditReminderViewModel @Inject constructor(private val reminderUseCases: ReminderUseCases, savedStateHandle: SavedStateHandle) :
    ViewModel() {

    private var getReminderColorPalette: List<Color> = Reminder.defaultRemainderColors


    private val _reminderTitle = mutableStateOf(NoteTextFieldState(hint = "Enter Title..."))
    val reminderTitle: State<NoteTextFieldState> = _reminderTitle

    private val _reminderContent = mutableStateOf(NoteTextFieldState(hint = "Enter content"))
    val reminderContent: State<NoteTextFieldState> = _reminderContent

    private val _reminderColor = mutableStateOf(getReminderColorPalette.indexOf(getReminderColorPalette.random()))
    val reminderColor: State<Int> = _reminderColor

    private val _reminderLocation = mutableStateOf<LocationSelectorState>(LocationSelectorState(isSelected = false))
    val reminderLocation: MutableState<LocationSelectorState> = _reminderLocation

    private val _reminderTimestamp = mutableStateOf<ReminderTimeSelectorState>(ReminderTimeSelectorState(isSelected = false))
    val reminderTimestamp: MutableState<ReminderTimeSelectorState> = _reminderTimestamp

    private val _eventFLow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFLow.asSharedFlow()

    fun updateReminderColor(color: Int, iteratedReminderColor: List<Color>) {
        _reminderColor.value = color
        getReminderColorPalette = iteratedReminderColor
    }

    private var currentReminderId: Int? = null

    init {
        savedStateHandle.get<Int>("reminderId")?.let { noteId ->
            if (noteId != -1) {
                viewModelScope.launch {
                    reminderUseCases.getReminder(noteId)?.also { reminder ->
                        currentReminderId = reminder.id
                        _reminderTitle.value = reminderTitle.value.copy(
                            text = reminder.title,
                            isHintVisible = false
                        )
                        _reminderContent.value = reminderContent.value.copy(
                            text = reminder.content,
                            isHintVisible = false
                        )
                        reminder.location?.let {
                            _reminderLocation.value = reminderLocation.value.copy(
                                location = it,
                                isSelected = true
                            )
                        }
                        reminder.remindTime?.let {
                            val (hour, minute) = convertTimestampToTime(reminder.remindTime)
                            _reminderTimestamp.value = reminderTimestamp.value.copy(
                                isSelected = true,
                                hour = hour,
                                minute = minute,
                                date = reminder.remindTime
                            )
                        }
                        _reminderColor.value = reminder.color
                    }
                }
            }
        }
    }

    fun onEvent(event: AddEditReminderEvent) {
        when (event) {
            is AddEditReminderEvent.EnteredTitle -> {
                _reminderTitle.value = reminderTitle.value.copy(
                    text = event.value
                )
            }

            is AddEditReminderEvent.ChangeTitleFocus -> {
                _reminderTitle.value = reminderTitle.value.copy(
                    isHintVisible = !event.focusState.isFocused && reminderTitle.value.text.isBlank()
                )
            }

            is AddEditReminderEvent.EnteredContent -> {
                _reminderContent.value = reminderContent.value.copy(
                    text = event.value
                )
            }

            is AddEditReminderEvent.ChangeContentFocus -> {
                _reminderContent.value = reminderContent.value.copy(
                    isHintVisible = !event.focusState.isFocused && reminderContent.value.text.isBlank()
                )
            }

            is AddEditReminderEvent.ChangeColor -> {
                _reminderColor.value = event.color
            }

            is AddEditReminderEvent.ChangeLocation -> {
                _reminderLocation.value = reminderLocation.value.copy(
                    location = event.location,
                )

            }

            is AddEditReminderEvent.ChangeLocationSelection -> {
                _reminderLocation.value = reminderLocation.value.copy(
                    isSelected = event.isSelected
                )
            }

            is AddEditReminderEvent.ChangeReminderTime -> {


                _reminderTimestamp.value = reminderTimestamp.value.copy(
                    hour = event.timeTriple?.first,
                    minute = event.timeTriple?.second,
                    date = event.timeTriple?.third
                )
            }

            is AddEditReminderEvent.ChangeTimeSelection -> {
                _reminderTimestamp.value = reminderTimestamp.value.copy(
                    isSelected = event.isSelected
                )
            }

            is AddEditReminderEvent.SaveReminder -> {
                var newReminder = Reminder(
                    id = currentReminderId,
                    title = reminderTitle.value.text,
                    content = reminderContent.value.text,
                    timestamp = System.currentTimeMillis(),
                    color = reminderColor.value,
                    location = if (reminderLocation.value.isSelected) reminderLocation.value.location else null,
                    remindTime = if (reminderTimestamp.value.isSelected) convertTimeToTimestamp(
                        reminderTimestamp.value.hour,
                        reminderTimestamp.value.minute,
                        reminderTimestamp.value.date
                    ) else null

                )

                viewModelScope.launch {
                    try {
                        reminderUseCases.addReminder(newReminder)
                        _eventFLow.emit(UiEvent.SaveNote)

                    } catch (e: InvalidNoteException) {
                        _eventFLow.emit(UiEvent.ShowSnackBar(message = e.message ?: "Couldn't save note"))
                    }

                }
            }


        }
    }


    sealed class UiEvent {

        // this for holding event of snackbar after actions like screen rotation
        data class ShowSnackBar(val message: String) : UiEvent()

        // this for after save note event. in our scenario navigate back
        object SaveNote : UiEvent()
    }
}