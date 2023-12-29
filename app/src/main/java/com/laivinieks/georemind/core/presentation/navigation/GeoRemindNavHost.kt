package com.laivinieks.georemind.core.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.laivinieks.georemind.core.util.Screen
import com.laivinieks.georemind.feature_note.presentation.FeatureNoteNavHost
import com.laivinieks.georemind.feature_reminder.presentation.FeatureReminderNavHost
import com.laivinieks.georemind.feature_reminder.presentation.reminders.RemindersScreen

@Composable
fun GeoRemindNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = Screen.RemainderScreen.route,
        modifier = modifier
    ) {
        composable(route = Screen.NotesScreen.route) {
            FeatureNoteNavHost()
        }
        composable(route = Screen.RemainderScreen.route) {
           FeatureReminderNavHost()
        }

    }
}