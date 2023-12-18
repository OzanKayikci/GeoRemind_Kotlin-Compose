package com.laivinieks.georemind.core.presentation.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector4D
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import com.laivinieks.georemind.feature_reminder.presentation.add_edit_remainder.AddEditReminderEvent
import com.laivinieks.georemind.feature_reminder.presentation.add_edit_remainder.AddEditReminderViewModel
import com.laivinieks.georemind.ui.theme.CustomColorsPalette
import com.laivinieks.georemind.ui.theme.iterateOverNoteColors
import kotlinx.coroutines.launch

@Composable
fun ColorPicker(modifier: Modifier = Modifier,
                noteColors: List<Color>,
                selectedColor:Int,
                newColor:(Int)->Unit,

) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        noteColors.forEach { color ->

            Box(
                modifier = Modifier
                    .size(50.dp)
                    .shadow(15.dp, CircleShape)
                    .clip(CircleShape)
                    .background(color)
                    .border(
                        width = 3.dp,
                        color = if (selectedColor == noteColors.indexOf(color)) {
                            MaterialTheme.colorScheme.onPrimaryContainer
                        } else Color.Transparent,
                        shape = CircleShape
                    )
                    .clickable {

                        newColor(noteColors.indexOf(color))
                    }
            ) {

            }
        }
    }
}