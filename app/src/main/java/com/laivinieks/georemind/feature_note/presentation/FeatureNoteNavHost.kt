package com.laivinieks.georemind.feature_note.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.laivinieks.georemind.feature_note.presentation.add_edit_note.AddEditNoteScreen

import com.laivinieks.georemind.feature_note.presentation.notes.NotesScreen
import com.laivinieks.georemind.feature_note.presentation.util.FeatureScreen

@Composable
fun FeatureNoteNavHost(
    modifier: Modifier = Modifier,
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = FeatureScreen.NotesFeatureScreen.route,
        modifier = modifier
    ) {
        composable(route = FeatureScreen.NotesFeatureScreen.route) {
            NotesScreen(navController = navController)
        }

        composable(route = FeatureScreen.AddEditNoteFeatureScreen.route +
                "?noteId={noteId}&noteColor={noteColor}", // this parameters are optional

            arguments = listOf(
                navArgument(name = "noteId") {
                    type = NavType.IntType
                    defaultValue = -1
                },
                navArgument(name = "noteColor") {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ) {
            val color = it.arguments?.getInt("noteColor") ?: -1
            AddEditNoteScreen(navController = navController, noteColor = color)
        }

    }

}