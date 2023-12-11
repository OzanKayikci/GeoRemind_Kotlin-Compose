package com.laivinieks.georemind.feature_note.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.laivinieks.georemind.feature_note.domain.modal.Note

@Database(
    entities = [Note::class],
    version = 1
)
abstract class NoteDatabase:RoomDatabase() {

    abstract val noteDao:NoteDao

    companion object{
        const val DATABASE_NAME = "note_db"
    }
}