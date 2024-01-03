package com.laivinieks.georemind.core.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign

@Composable
fun TransparentHintTextField(
    text: String,
    hint: String,
    modifier: Modifier = Modifier,
    isHintVisible: Boolean = true,
    onValueChange: (String) -> Unit,
    textStyle: TextStyle = TextStyle(color = MaterialTheme.colorScheme.onPrimaryContainer),
    maxLine: Int = 1,
    alignment: Alignment = Alignment.TopStart,
    onFocusChange: (FocusState) -> Unit
) {
    Box(modifier = modifier) {
        BasicTextField(
            value = text,
            onValueChange = onValueChange,
            maxLines = maxLine,
            textStyle = textStyle,
            modifier = Modifier

                .onFocusChanged {
                    onFocusChange(it)
                },

            ) { innerTextField ->
            Box(modifier = Modifier, alignment) {
                if (isHintVisible) Text(text = hint, style = textStyle, color = LocalContentColor.current.copy(alpha = 0.5f))
                innerTextField()
            }

        }
    }
}
