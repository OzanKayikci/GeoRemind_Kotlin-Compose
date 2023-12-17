package com.laivinieks.georemind.feature_note.presentation.add_edit_note

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.laivinieks.georemind.feature_note.domain.modal.InvalidNoteException
import com.laivinieks.georemind.feature_note.domain.modal.Note
import com.laivinieks.georemind.feature_note.domain.use_case.NoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(private val noteUseCases: NoteUseCases, savedStateHandle: SavedStateHandle) : ViewModel() {

    private var getNoteColorPalette: List<Color> = Note.defaultNoteColors


    private val _noteTitle = mutableStateOf(NoteTextFieldState(hint = "Enter Title..."))
    val noteTitle: State<NoteTextFieldState> = _noteTitle

    private val _noteContent = mutableStateOf(NoteTextFieldState(hint = "Enter content"))
    val noteContent: State<NoteTextFieldState> = _noteContent

    private val _noteColor = mutableStateOf(Note.defaultNoteColors.random().toArgb())
    val noteColor: State<Int> = _noteColor

    private val _eventFLow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFLow.asSharedFlow()

    fun updateNoteColor(color: Int, iteratedNoteColor: List<Color>) {
        _noteColor.value = color
        getNoteColorPalette = iteratedNoteColor
    }

    private var currentNoteId: Int? = null

    init {
        savedStateHandle.get<Int>("noteId")?.let { noteId ->
            if (noteId != -1) {
                viewModelScope.launch {
                    noteUseCases.getNote(noteId)?.also { note ->
                        currentNoteId = note.id
                        _noteTitle.value = noteTitle.value.copy(
                            text = note.title,
                            isHintVisible = false
                        )
                        _noteContent.value = noteContent.value.copy(
                            text = note.content,
                            isHintVisible = false
                        )

                        _noteColor.value = getNoteColorPalette[note.color].toArgb()
                    }
                }
            }
        }
    }

    fun onEvent(event: AddEditNoteEvent) {
        when (event) {
            is AddEditNoteEvent.EnteredTitle -> {
                _noteTitle.value = noteTitle.value.copy(
                    text = event.value
                )
            }

            is AddEditNoteEvent.ChangeTitleFocus -> {
                _noteTitle.value = noteTitle.value.copy(
                    isHintVisible = !event.focusState.isFocused && noteTitle.value.text.isBlank()
                )
            }

            is AddEditNoteEvent.EnteredContent -> {
                _noteContent.value = noteContent.value.copy(
                    text = event.value
                )
            }

            is AddEditNoteEvent.ChangeContentFocus -> {
                _noteContent.value = noteContent.value.copy(
                    isHintVisible = !event.focusState.isFocused && noteContent.value.text.isBlank()
                )
            }

            is AddEditNoteEvent.ChangeColor -> {
                _noteColor.value = event.color
            }

            is AddEditNoteEvent.SaveNote -> {
                var newNote = Note(
                    id = currentNoteId,
                    title = noteTitle.value.text,
                    content = noteContent.value.text,
                    timestamp = System.currentTimeMillis(),
                    color = noteColor.value
                )

                viewModelScope.launch {
                    try {
                        noteUseCases.addNote(newNote)
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