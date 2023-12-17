package com.laivinieks.georemind.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import javax.annotation.concurrent.Immutable

@Immutable
data class CustomColorsPalette(
    val noteColor1: Color = Color.Unspecified,
    val noteColor2: Color = Color.Unspecified,
    val noteColor3: Color = Color.Unspecified,
    val noteColor4: Color = Color.Unspecified,
    val noteColor5: Color = Color.Unspecified,
    val noteColor6: Color = Color.Unspecified,
)


val LocalCustomColorsPalette = staticCompositionLocalOf { CustomColorsPalette() }

fun iterateOverNoteColors(customColorsPalette: CustomColorsPalette):List<Color> {
   return customColorsPalette.run {
        return@run listOf(noteColor1, noteColor2, noteColor3, noteColor4, noteColor5, noteColor6)

    }
}