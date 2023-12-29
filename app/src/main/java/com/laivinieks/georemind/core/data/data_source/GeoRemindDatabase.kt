package com.laivinieks.georemind.core.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.laivinieks.georemind.feature_note.data.data_source.NoteDao
import com.laivinieks.georemind.feature_note.domain.modal.Note
import com.laivinieks.georemind.feature_reminder.data.data_source.ReminderDao
import com.laivinieks.georemind.feature_reminder.domain.model.Reminder

@Database(
    entities = [Note::class, Reminder::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class GeoRemindDatabase : RoomDatabase() {

    abstract val noteDao: NoteDao
    abstract val reminderDao: ReminderDao

    companion object {
        const val DATABASE_NAME = "georemind_db"
    }
}