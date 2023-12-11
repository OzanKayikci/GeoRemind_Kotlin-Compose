package com.laivinieks.georemind.feature_note.domain.usecase

import javax.inject.Inject


/**
 * we did it because there  can be many use cases. and therefore our Viewmodel constructor will be very long.
 * that is why we create a data class. we will just take this data class as parameter in constructor
 * */
data class NoteUseCases (
    val getNotes: GetNotes,
    val  deleteNote: DeleteNote,
    val addNote: AddNote,
    val getNote: GetNote
)
