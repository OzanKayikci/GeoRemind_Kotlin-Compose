package com.laivinieks.georemind.feature_reminder.presentation.add_edit_remainder

import android.location.Location
import androidx.compose.ui.focus.FocusState

sealed class AddEditReminderEvent {
    data class EnteredTitle(val value:String):AddEditReminderEvent()
    data class ChangeTitleFocus( val focusState: FocusState) : AddEditReminderEvent()
    data class EnteredContent(val value:String):AddEditReminderEvent()
    data class ChangeContentFocus( val focusState: FocusState) : AddEditReminderEvent()

    data class ChangeColor(val color:Int):AddEditReminderEvent()
    data class ChangeLocation(val location: Location):AddEditReminderEvent()
    data class ChangeReminderTime(val timeStamp:Long):AddEditReminderEvent()
    object SaveReminder:AddEditReminderEvent()
}