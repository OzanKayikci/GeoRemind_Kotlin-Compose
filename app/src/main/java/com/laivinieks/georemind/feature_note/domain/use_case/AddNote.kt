package com.laivinieks.georemind.feature_note.domain.use_case

import com.laivinieks.georemind.feature_note.domain.modal.InvalidNoteException
import com.laivinieks.georemind.feature_note.domain.modal.Note
import com.laivinieks.georemind.feature_note.domain.repository.NoteRepository

class AddNote(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(note: Note) {
        checkValidations(note)
        repository.insertModel(note)
    }

    @Throws(InvalidNoteException::class)
    private fun checkValidations(note: Note) {

        if (note.title.isBlank()) {

            throw InvalidNoteException("The title of the note can't be empty.")
        }
        if (note.content.isBlank()) {

            throw InvalidNoteException("The content of the note can't be empty.")
        }
    }
}