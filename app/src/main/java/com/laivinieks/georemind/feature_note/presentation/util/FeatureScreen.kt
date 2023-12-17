package com.laivinieks.georemind.feature_note.presentation.util

sealed class FeatureScreen (val route : String){
    object NotesFeatureScreen:FeatureScreen("note_screen")
    object AddEditNoteFeatureScreen:FeatureScreen("add_edit_note_screen")
}