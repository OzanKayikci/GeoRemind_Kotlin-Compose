package com.laivinieks.georemind.core.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.EditLocationAlt
import com.laivinieks.georemind.R

sealed class Screen(val route: String, val title: String, val icon: Int,val iconsFocused :Int) {
    object NotesScreen : Screen("note_screen", title = "Notes", icon = R.drawable.ic_bottom_notes, iconsFocused = R.drawable.ic_bottom_notes_focused)
    object RemainderScreen : Screen("remainder_screen", title = "Remainder", icon = R.drawable.ic_bottom_location_add, iconsFocused = R.drawable.ic_bottom_location_add_focused)
}