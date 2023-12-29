package com.laivinieks.georemind.feature_reminder.presentation.add_edit_remainder

import android.location.Location
import androidx.compose.ui.focus.FocusState
import com.laivinieks.georemind.feature_reminder.domain.model.LocationData

sealed class AddEditReminderEvent {
    data class EnteredTitle(val value: String) : AddEditReminderEvent()
    data class ChangeTitleFocus(val focusState: FocusState) : AddEditReminderEvent()
    data class EnteredContent(val value: String) : AddEditReminderEvent()
    data class ChangeContentFocus(val focusState: FocusState) : AddEditReminderEvent()

    data class ChangeColor(val color: Int) : AddEditReminderEvent()
    data class ChangeLocation(val location: LocationData?) : AddEditReminderEvent()
    data class ChangeLocationSelection(val isSelected: Boolean) : AddEditReminderEvent()
    data class ChangeReminderTime(val timeTriple: Triple<Int,Int,Long>?) : AddEditReminderEvent()
    data class ChangeTimeSelection(val isSelected: Boolean) : AddEditReminderEvent()
    object SaveReminder : AddEditReminderEvent()
}