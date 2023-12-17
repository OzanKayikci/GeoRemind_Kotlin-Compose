package com.laivinieks.georemind.core.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.laivinieks.georemind.core.util.Screen
import com.laivinieks.georemind.feature_note.presentation.FeatureNoteNavHost
import com.laivinieks.georemind.feature_note.presentation.add_edit_note.AddEditNoteScreen
import com.laivinieks.georemind.feature_note.presentation.notes.NotesScreen
import com.laivinieks.georemind.feature_note.presentation.util.FeatureScreen
import com.laivinieks.georemind.feature_remainder.presentation.remainders.RemaindersScreen

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