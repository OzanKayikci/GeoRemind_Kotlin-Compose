package com.laivinieks.georemind.feature_reminder.domain.model

import com.laivinieks.georemind.feature_note.domain.util.NoteOrder
import com.laivinieks.georemind.core.domain.util.OrderType
import com.laivinieks.georemind.feature_reminder.domain.util.ReminderOrder

data class ReminderState(
    val reminders:List<Reminder> = emptyList(),
    val reminderOrder: ReminderOrder = ReminderOrder.Date(OrderType.Descending),
    var isOrderSectionVisible:Boolean = false
)
