package com.laivinieks.georemind.feature_reminder.data.repository
import com.laivinieks.georemind.feature_reminder.data.data_source.ReminderDao
import com.laivinieks.georemind.feature_reminder.domain.model.LocationData

import com.laivinieks.georemind.feature_reminder.domain.model.Reminder
import com.laivinieks.georemind.feature_reminder.domain.repository.ReminderRepository
import kotlinx.coroutines.flow.Flow

class ReminderRepositoryImplementation(
    private val dao:ReminderDao

):ReminderRepository {
    override fun getAllSavedLocations(): Flow<List<Reminder>> {
        return dao.getAllLocationData()
    }

    override fun getModels(): Flow<List<Reminder>> {
        return dao.getReminder()
    }

    override suspend fun getModelById(id: Int): Reminder? {
       return dao.getReminderById(id)
    }

    override suspend fun insertModel(note: Reminder) {
      dao.insertReminder(note)
    }

    override suspend fun deleteModel(note: Reminder) {
        dao.deleteReminder(note)
    }

}