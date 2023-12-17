package com.laivinieks.georemind.core.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.laivinieks.georemind.core.util.Screen
import com.laivinieks.georemind.feature_note.presentation.FeatureNoteNavHost
import com.laivinieks.georemind.feature_reminder.presentation.remainders.RemaindersScreen

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
            RemaindersScreen(navController = navController)
        }
//        composable(route = FeatureScreen.AddEditNoteFeatureScreen.route +
//                "?noteId={noteId}&noteColor={noteColor}", // this parameters are optional
//
//            arguments = listOf(
//                navArgument(name = "noteId") {
//                    type = NavType.IntType
//                    defaultValue = -1
//                },
//                navArgument(name = "noteColor") {
//                    type = NavType.IntType
//                    defaultValue = -1
//                }
//            )
//        ) {
//            val color = it.arguments?.getInt("noteColor") ?: -1
//            AddEditNoteScreen(navController = navController, noteColor = color)
//        }

    }
}