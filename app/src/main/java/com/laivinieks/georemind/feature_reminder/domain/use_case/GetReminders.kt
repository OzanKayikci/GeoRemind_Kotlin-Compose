package com.laivinieks.georemind.feature_reminder.domain.use_case


import com.laivinieks.georemind.core.domain.util.OrderType
import com.laivinieks.georemind.feature_reminder.domain.model.Reminder
import com.laivinieks.georemind.feature_reminder.domain.repository.ReminderRepository
import com.laivinieks.georemind.feature_reminder.domain.util.ReminderOrder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetReminders(private val repository: ReminderRepository) {
    // use case should have just one public functions

    // invoke is an operator. so we overload invoke with operator keyword
    operator fun invoke(reminderOrder: ReminderOrder = ReminderOrder.Date(OrderType.Descending)): Flow<List<Reminder>> {
        return repository.getModels().map { reminders ->
            when (reminderOrder.orderType) {
                is OrderType.Ascending -> {
                    when (reminderOrder) {
                        is ReminderOrder.Title -> reminders.sortedBy { it.title.lowercase() }
                        is ReminderOrder.Color -> reminders.sortedBy { it.color }
                        is ReminderOrder.Date -> reminders.sortedBy { it.timestamp }
                        is ReminderOrder.ReminderTime -> reminders.sortedBy { it.remindTime }
                        is ReminderOrder.Location -> reminders.sortedBy { it.location?.latitude }
                    }
                }

                is OrderType.Descending -> {
                    when (reminderOrder) {
                        is ReminderOrder.Title -> reminders.sortedByDescending { it.title.lowercase() }
                        is ReminderOrder.Color -> reminders.sortedByDescending { it.color }
                        is ReminderOrder.Date -> reminders.sortedByDescending { it.timestamp }
                        is ReminderOrder.ReminderTime -> reminders.sortedByDescending { it.remindTime }
                        is ReminderOrder.Location -> reminders.sortedByDescending { it.location?.latitude }
                    }

                }
            }

        }
    }

}