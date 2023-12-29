package com.laivinieks.georemind.feature_reminder.domain.use_case

import com.laivinieks.georemind.feature_note.domain.modal.Note
import com.laivinieks.georemind.feature_note.domain.repository.NoteRepository
import com.laivinieks.georemind.feature_reminder.domain.model.Reminder
import com.laivinieks.georemind.feature_reminder.domain.repository.ReminderRepository

class DeleteReminder (private val repository: ReminderRepository) {
    suspend operator fun invoke(reminder:Reminder) {
        repository.deleteModel(reminder)
    }
}