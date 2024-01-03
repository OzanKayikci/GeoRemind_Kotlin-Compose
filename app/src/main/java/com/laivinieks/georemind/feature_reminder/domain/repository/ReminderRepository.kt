package com.laivinieks.georemind.feature_reminder.domain.repository

import com.laivinieks.georemind.core.data.repository.GeoRemindRepository
import com.laivinieks.georemind.feature_reminder.domain.model.LocationData
import com.laivinieks.georemind.feature_reminder.domain.model.Reminder
import kotlinx.coroutines.flow.Flow


interface ReminderRepository : GeoRemindRepository<Reminder> {

    fun getAllSavedLocations(): Flow<List<Reminder>>
}