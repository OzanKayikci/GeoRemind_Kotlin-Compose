package com.laivinieks.georemind.feature_note.presentation.util

sealed class NoteFeatureScreen (val route : String){
    object NotesScreen:NoteFeatureScreen("note_screen")
    object AddEditNoteScreen:NoteFeatureScreen("add_edit_note_screen")
}