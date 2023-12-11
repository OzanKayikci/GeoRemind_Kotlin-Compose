package com.laivinieks.georemind.feature_note.domain.repository

import com.laivinieks.georemind.feature_note.domain.modal.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun getNotes(): Flow<List<Note>>

    suspend fun getNoteById(id: Int): Note?
    suspend fun insertNote(note: Note)
    suspend fun deleteNote(note: Note)
}