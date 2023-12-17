package com.laivinieks.georemind.feature_note.domain.use_case

import com.laivinieks.georemind.feature_note.domain.modal.Note
import com.laivinieks.georemind.feature_note.domain.repository.NoteRepository

class DeleteNote (private val repository: NoteRepository) {
    suspend operator fun invoke(note: Note) {
        repository.deleteModel(note)
    }
}