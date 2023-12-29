package com.laivinieks.georemind.feature_note.presentation.notes

import com.laivinieks.georemind.feature_note.domain.modal.Note
import com.laivinieks.georemind.feature_note.domain.util.NoteOrder
import com.laivinieks.georemind.core.domain.util.OrderType

/**
* we did this because we want to keep states in our viewmodel
 * for survive after screen rotations
* */
data class NotesState (
    val notes:List<Note> = emptyList(),
    val noteOder : NoteOrder = NoteOrder.Date(OrderType.Descending),
    val isOrderSectionVisible :Boolean = false
)