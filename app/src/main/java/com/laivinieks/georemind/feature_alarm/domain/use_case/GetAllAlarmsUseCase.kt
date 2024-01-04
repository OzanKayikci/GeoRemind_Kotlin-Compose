package com.laivinieks.georemind.feature_alarm.domain.use_case

import com.laivinieks.georemind.feature_reminder.domain.model.Reminder
import com.laivinieks.georemind.feature_reminder.domain.repository.ReminderRepository
import kotlinx.coroutines.flow.Flow

class GetAllAlarmsUseCase(private val repository: ReminderRepository) {
    operator fun invoke(): Flow<List<Reminder>> {
        return repository.getAllSavedAlarms()
    }

}