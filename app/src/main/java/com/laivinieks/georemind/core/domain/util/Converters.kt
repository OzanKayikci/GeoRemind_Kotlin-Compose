package com.laivinieks.georemind.core.domain.util

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.graphics.ColorUtils

object Converters {

    fun getSecondaryColor(color: Color, ratio:Float): Color {
        return Color(ColorUtils.blendARGB(color.toArgb(), 0xffffff, ratio))
    }
}