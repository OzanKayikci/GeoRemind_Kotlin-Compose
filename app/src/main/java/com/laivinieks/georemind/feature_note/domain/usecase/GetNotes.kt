package com.laivinieks.georemind.feature_note.domain.usecase

import android.util.Log
import com.laivinieks.georemind.feature_note.domain.modal.Note
import com.laivinieks.georemind.feature_note.domain.repository.NoteRepository
import com.laivinieks.georemind.feature_note.domain.util.NoteOrder
import com.laivinieks.georemind.feature_note.domain.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.map

class GetNotes(private val repository: NoteRepository) {
    // use case should have just one public functions

    // invoke is an operator. so we overload invoke with operator keyword
    operator fun invoke(noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending)): Flow<List<Note>> {
        return repository.getNotes().map { notes ->
            when (noteOrder.orderType) {
                is OrderType.Ascending -> {
                    when (noteOrder) {
                        is NoteOrder.Title -> notes.sortedBy { it.title.lowercase() }
                        is NoteOrder.Color -> notes.sortedBy { it.color }
                        is NoteOrder.Date -> notes.sortedBy { it.timestamp }

                    }
                }

                is OrderType.Descending -> {
                    when (noteOrder) {
                        is NoteOrder.Title -> notes.sortedByDescending { it.title.lowercase() }
                        is NoteOrder.Color -> notes.sortedByDescending { it.color }
                        is NoteOrder.Date -> notes.sortedByDescending { it.timestamp }
                    }

                }
            }

        }
    }

}