package com.laivinieks.georemind.feature_note.domain.use_case

import com.laivinieks.georemind.feature_note.domain.modal.Note
import com.laivinieks.georemind.feature_note.domain.repository.NoteRepository

class GetNote(private val repository: NoteRepository) {
    suspend operator fun invoke(id: Int): Note? {
        return repository.getModelById(id)
    }
}