package com.laivinieks.georemind.feature_note.presentation.notes

import androidx.compose.foundation.Canvas

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column


import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Remove

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.graphics.ColorUtils
import com.laivinieks.georemind.feature_note.domain.modal.Note
import com.laivinieks.georemind.feature_note.domain.util.Converters

@Composable
fun NoteItem(
    note: Note,
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 16.dp,
    cutCornerSize: Dp = 60.dp,
    onDeleteClick: () -> Unit
) {
    Box(modifier = modifier) {
        Canvas(modifier = Modifier.matchParentSize()) {
            val clipPath = Path().apply {
                lineTo(size.width - cutCornerSize.toPx(), 0f)
                lineTo(size.width, cutCornerSize.toPx())
                lineTo(size.width, size.height)
                lineTo(0f, size.height)
                close()
            }
            clipPath(clipPath) {
                drawRoundRect(
                    color = Converters.getSecondaryColor(Color((note.color))),
                    size = size,
                    cornerRadius = CornerRadius(cornerRadius.toPx())
                )
                drawRoundRect(
                    color = Color(
                        note.color
                    ),
                    topLeft = Offset(size.width - cutCornerSize.toPx(), -100f),
                    size = Size(cutCornerSize.toPx() + 100f, cutCornerSize.toPx() + 100f),
                    cornerRadius = CornerRadius(cornerRadius.toPx())
                )
            }

        }
        Column(
            modifier
                .fillMaxSize()
                .padding(16.dp)

        ) {
            Text(
                text = note.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimary,
                maxLines = 1,
                modifier = modifier.fillMaxWidth(0.8f),
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = note.content,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = modifier.fillMaxWidth(1f),
                maxLines = 10,
                overflow = TextOverflow.Ellipsis
            )
        }
        IconButton(
            onClick = { onDeleteClick }, modifier = Modifier
                .align(Alignment.TopEnd)
                .fillMaxWidth(0.1f)
                .offset((-18).dp, 18.dp)
        ) {
            Icon(
                imageVector = Icons.Rounded.Remove,
                contentDescription = "Delete Note",
                Modifier.fillMaxWidth(),
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun NoteItemPreview() {
    MaterialTheme {
        Surface {
            NoteItem(note = Note(id = 0, title = "Title ", content = "Content", color = Note.noteColors[4].toArgb(), timestamp = 0)) {

            }
        }
    }
}