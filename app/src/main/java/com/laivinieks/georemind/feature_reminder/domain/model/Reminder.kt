package com.laivinieks.georemind.feature_reminder.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.laivinieks.georemind.core.domain.model.BaseModel
import com.laivinieks.georemind.ui.theme.*

@Entity
data class Reminder(
    @PrimaryKey
    val id: Int? = null,
    val title: String,
    val content: String,
    val timestamp: Long,

    val location: LocationData?,
    val remindTime: Long? = null,
    val color: Int, // index of noteColor
):BaseModel {
    companion object {
        val defaultRemainderColors = listOf(MarkColor1, MarkColor2, MarkColor3, MarkColor4, MarkColor5, MarkColor6)

    }
}

class InvalidRemainderException(message: String) : Exception(message)
