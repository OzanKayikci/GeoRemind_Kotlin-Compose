package com.laivinieks.georemind.feature_note.domain.util

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.graphics.ColorUtils

object Converters {

    fun getSecondaryColor(color: Color): Color {
        return Color(ColorUtils.blendARGB(color.toArgb(), 0xffffff, 0.5f))
    }
}