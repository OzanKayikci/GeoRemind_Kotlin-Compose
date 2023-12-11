package com.laivinieks.georemind.feature_note.domain.usecase

import com.laivinieks.georemind.feature_note.domain.modal.Note
import com.laivinieks.georemind.feature_note.domain.repository.NoteRepository

class DeleteNote (private val repository: NoteRepository) {
    suspend operator fun invoke(note: Note) {
        repository.deleteNote(note)
    }
}