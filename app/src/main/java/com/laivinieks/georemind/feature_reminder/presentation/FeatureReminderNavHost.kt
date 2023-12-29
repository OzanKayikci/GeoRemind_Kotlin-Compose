package com.laivinieks.georemind.feature_reminder.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.laivinieks.georemind.feature_note.presentation.add_edit_note.AddEditNoteScreen

import com.laivinieks.georemind.feature_note.presentation.notes.NotesScreen
import com.laivinieks.georemind.feature_reminder.presentation.add_edit_remainder.AddEditReminderScreen
import com.laivinieks.georemind.feature_reminder.presentation.reminders.RemindersScreen
import com.laivinieks.georemind.feature_reminder.presentation.util.ReminderFeatureScreen

@Composable
fun FeatureReminderNavHost(
    modifier: Modifier = Modifier,
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = ReminderFeatureScreen.RemindersScreen.route,
        modifier = modifier
    ) {
        composable(route = ReminderFeatureScreen.RemindersScreen.route) {
            RemindersScreen(navController = navController)
        }

        composable(route = ReminderFeatureScreen.AddEditReminderScreen.route +
                "?reminderId={reminderId}&reminderColor={reminderColor}", // this parameters are optional

            arguments = listOf(
                navArgument(name = "reminderId") {
                    type = NavType.IntType
                    defaultValue = -1
                },
                navArgument(name = "reminderColor") {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ) {
            val color = it.arguments?.getInt("reminderColor") ?: -1
            AddEditReminderScreen(navController = navController, reminderColor = color)
        }

    }

}