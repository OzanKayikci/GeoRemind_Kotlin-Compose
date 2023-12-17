package com.laivinieks.georemind.feature_reminder.data.data_source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.laivinieks.georemind.feature_note.domain.modal.Note
import com.laivinieks.georemind.feature_reminder.domain.model.Reminder
import kotlinx.coroutines.flow.Flow

@Dao
interface ReminderDao {

    // this function returns flow so we don't need suspend
    @Query("SELECT * FROM reminder")
    fun getReminder(): Flow<List<Reminder>>

    @Query("SELECT * FROM reminder WHERE id = :id")
    suspend fun getReminderById(id: Int): Reminder?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReminder(reminder: Reminder)

    @Delete
    suspend fun deleteReminder(reminder: Reminder)
}