package com.laivinieks.georemind.feature_reminder.presentation.add_edit_remainder.components.time

data class ReminderTimeSelectorState(
    val isSelected: Boolean,
    val hour: Int? = null,
    val minute:Int? = null,
    val date:Long? = null
)