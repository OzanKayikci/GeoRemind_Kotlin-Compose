package com.laivinieks.georemind.feature_reminder.domain.use_case

import com.laivinieks.georemind.feature_reminder.domain.model.Reminder
import com.laivinieks.georemind.feature_reminder.domain.repository.ReminderRepository

class GetReminder(private val repository: ReminderRepository) {
    suspend operator fun invoke(id: Int): Reminder? {
        return repository.getModelById(id)
    }
}