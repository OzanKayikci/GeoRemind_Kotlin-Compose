package com.laivinieks.georemind.feature_reminder.presentation.reminders


import com.laivinieks.georemind.feature_reminder.domain.model.Reminder
import com.laivinieks.georemind.feature_reminder.domain.util.ReminderOrder


sealed class RemindersEvent {
    data class Order(val reminderOrder: ReminderOrder) : RemindersEvent()
    data class  DeleteReminder(val reminder: Reminder):RemindersEvent()

    object  RestoreReminder:RemindersEvent()
    object ToggleOrderSection:RemindersEvent()
}
