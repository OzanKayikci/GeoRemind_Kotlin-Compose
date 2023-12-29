package com.laivinieks.georemind.feature_reminder.presentation.util

sealed class ReminderFeatureScreen (val route : String){
    object RemindersScreen:ReminderFeatureScreen("reminders_screen")
    object AddEditReminderScreen:ReminderFeatureScreen("add_edit_reminder_screen")
}