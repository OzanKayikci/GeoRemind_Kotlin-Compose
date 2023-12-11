package com.laivinieks.georemind.feature_note.data.data_source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.laivinieks.georemind.feature_note.domain.modal.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    // this function returns flow so we don't need suspend
    @Query("SELECT * FROM note")
    fun getNotes(): Flow<List<Note>>

    @Query("SELECT * FROM note WHERE id = :id")
    suspend fun getNoteById(id: Int): Note?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotes(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)
}