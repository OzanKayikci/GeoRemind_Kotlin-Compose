package com.laivinieks.georemind.feature_note.data.repository

import com.laivinieks.georemind.feature_note.data.data_source.NoteDao
import com.laivinieks.georemind.feature_note.domain.modal.Note
import com.laivinieks.georemind.feature_note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class NoteRepositoryImplementation(
    private val dao:NoteDao

):NoteRepository {
    override fun getNotes(): Flow<List<Note>> {
        return dao.getNotes()
    }

    override suspend fun getNoteById(id: Int): Note? {
       return dao.getNoteById(id)
    }

    override suspend fun insertNote(note: Note) {
      dao.insertNotes(note)
    }

    override suspend fun deleteNote(note: Note) {
        dao.deleteNote(note)
    }
}