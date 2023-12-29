package com.laivinieks.georemind.feature_reminder.domain.use_case

import com.laivinieks.georemind.feature_note.domain.modal.InvalidNoteException

import com.laivinieks.georemind.feature_reminder.domain.model.Reminder
import com.laivinieks.georemind.feature_reminder.domain.repository.ReminderRepository

class AddReminder(
    private val repository: ReminderRepository
) {
    suspend operator fun invoke(reminder: Reminder) {
        checkValidations(reminder)
        repository.insertModel(reminder)
    }

    @Throws(InvalidNoteException::class)
    private fun checkValidations(reminder: Reminder) {

        if (reminder.title.isBlank()) {

            throw InvalidNoteException("The title of the note can't be empty.")
        }
        if (reminder.content.isBlank()) {

            throw InvalidNoteException("The content of the note can't be empty.")
        }
    }
}