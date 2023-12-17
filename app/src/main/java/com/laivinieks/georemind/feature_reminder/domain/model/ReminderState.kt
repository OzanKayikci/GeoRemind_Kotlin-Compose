package com.laivinieks.georemind.feature_reminder.domain.model

import com.laivinieks.georemind.feature_note.domain.util.NoteOrder
import com.laivinieks.georemind.core.domain.util.OrderType

data class ReminderState(
    val notes:List<Reminder> = emptyList(),
    val noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    var isOrderSectionVisible:Boolean = false
)
