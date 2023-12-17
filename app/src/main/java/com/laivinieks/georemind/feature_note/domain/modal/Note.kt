package com.laivinieks.georemind.feature_note.domain.modal

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.laivinieks.georemind.R
import com.laivinieks.georemind.ui.theme.*

@Entity
data class Note(
    @PrimaryKey
    val id: Int? = null,
    val title: String,
    val content: String,
    val timestamp: Long,
    val color: Int, // index of noteColor
) {
    companion object {
        val defaultNoteColors = listOf(MarkColor1, MarkColor2, MarkColor3, MarkColor4, MarkColor5, MarkColor6)

    }
}

class InvalidNoteException(message: String) : Exception(message)
