package com.laivinieks.georemind.feature_note.presentation.notes


import androidx.compose.foundation.Canvas
import androidx.compose.foundation.isSystemInDarkTheme

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding

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
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.rotate

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

import com.laivinieks.georemind.feature_note.domain.modal.Note
import com.laivinieks.georemind.core.domain.util.Converters
import com.laivinieks.georemind.ui.theme.LocalCustomColorsPalette
import com.laivinieks.georemind.ui.theme.iterateOverNoteColors


@Composable
fun NoteItem(
    note: Note,
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 16.dp,
    cutCornerSize: Dp = 40.dp,
    onDeleteClick: () -> Unit
) {

    val noteColor = iterateOverNoteColors(LocalCustomColorsPalette.current)[note.color].toArgb()

    Box(modifier = modifier) {
        var clipPathPublic = Path()

        CardBackgroundCanvas(
            modifier = Modifier.matchParentSize(),
            cornerRadius = cornerRadius,
            cutCornerSize = cutCornerSize,
            noteColor = noteColor
        ) {
            clipPathPublic = it
        }

        Column(
            modifier
                .fillMaxSize()
                .padding(16.dp)

        ) {
            Text(
                text = note.title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                maxLines = 1,
                modifier = modifier.fillMaxWidth(0.8f),
                overflow = TextOverflow.Ellipsis,

                )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = note.content,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium),
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = modifier.fillMaxWidth(1f),
                maxLines = 10,
                overflow = TextOverflow.Ellipsis,
            )
        }
// bottom corner shadow
        Canvas(
            modifier = Modifier
                .matchParentSize()
                .blur(8.dp)



        ) {
            clipPath(clipPathPublic) {
                drawRoundRect(
                    color =
                    Color.Black.copy(alpha = 0.2f),
                    topLeft = Offset(size.width - cutCornerSize.toPx()-10f , size.height - 150f),
                    size = Size(cutCornerSize.toPx(), cutCornerSize.toPx()),
                    cornerRadius = CornerRadius(10.dp.toPx()),

                )
            }


        }
        // bottom right corner curve of card

        Canvas(modifier = Modifier.matchParentSize()) {
            clipPath(clipPathPublic) {
                drawRoundRect(
                    color = Color(
                        noteColor
                    ),
                    topLeft = Offset(size.width - cutCornerSize.toPx(), size.height - 140f),
                    size = Size(cutCornerSize.toPx() + 100f, cutCornerSize.toPx() + 100f),
                    cornerRadius = CornerRadius(cornerRadius.toPx()),
                )
            }
        }

        IconButton(
            onClick = onDeleteClick, modifier = Modifier
                .align(Alignment.BottomEnd)
                .fillMaxWidth(0.1f)
                .offset((-6).dp, (-5).dp)
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


// this is main part of card
@Composable
private fun CardBackgroundCanvas(
    modifier: Modifier = Modifier,
    cornerRadius: Dp,
    cutCornerSize: Dp,
    noteColor: Int,
    clipPathPublic: (Path) -> Unit
) {
    val colorRatio = if (isSystemInDarkTheme()) 0.2f else 0.4f
    Canvas(modifier = modifier) {

        val clipPath = Path().apply {
            lineTo(size.width, 0f)
            lineTo(size.width, size.height - cutCornerSize.toPx())
            lineTo(size.width - cutCornerSize.toPx(), size.height)
            lineTo(0f, size.height)
            close()
        }
        clipPathPublic(clipPath)

        clipPath(clipPath) {
            drawRoundRect(
                color = Converters.getSecondaryColor(Color(noteColor), colorRatio),
//                brush = Brush.verticalGradient(
//                    colors = listOf(
//                        Converters.getSecondaryColor(Color(noteColor), 0.4f),
//                        Converters.getSecondaryColor(Color(noteColor), 0.2f),
//                        Converters.getSecondaryColor(Color(noteColor), 0.2f)
//                    )
//                ),
                size = size,

                cornerRadius = CornerRadius(cornerRadius.toPx()),
            )

        }

    }
}

//This for white effect of card
//@Composable
//private fun CardFrontBackgroundCanvas(
//    modifier: Modifier,
//    cornerRadius: Dp,
//    clipPathPublic: Path
//) {
//    Canvas(
//        modifier = modifier
//            .blur(12.dp)
//    ) {
//        clipPath(clipPathPublic) {
//            drawRoundRect(
//                color = Converters.getSecondaryColor(Color.White, 0.8f),
//                size = Size(size.width, size.height - 280f),
//                topLeft = Offset(0f, 180f),
//                cornerRadius = CornerRadius(cornerRadius.toPx()),
//
//                )
//
//        }
//    }
//}


//The shadow of curve of card
//@Composable
//fun BottomRightCornerCurveShadow(
//    modifier: Modifier,
//    cornerRadius: Dp,
//    cutCornerSize: Dp,
//    clipPathPublic: Path
//) {
//    Canvas(
//        modifier = modifier
//            .blur(10.dp)
//    ) {
//        clipPath(clipPathPublic) {
//            drawRoundRect(
//                color =
//                Color.Gray,
//                topLeft = Offset(size.width - cutCornerSize.toPx() - 10f, size.height - 150f),
//                size = Size(cutCornerSize.toPx(), cutCornerSize.toPx()),
//                cornerRadius = CornerRadius(cornerRadius.toPx()),
//            )
//        }
//
//
//    }
//}
//
////The curve of card
//@Composable
//fun BottomRightCornerCurve(
//    modifier: Modifier,
//    cornerRadius: Dp,
//    cutCornerSize: Dp,
//    clipPathPublic: Path,
//    noteColor: Int
//) {
//    Canvas(modifier = modifier) {
//        clipPath(clipPathPublic) {
//            drawRoundRect(
//                color = Color(
//                    noteColor
//                ),
//                topLeft = Offset(size.width - cutCornerSize.toPx(), size.height - 140f),
//                size = Size(cutCornerSize.toPx() + 100f, cutCornerSize.toPx() + 100f),
//                cornerRadius = CornerRadius(cornerRadius.toPx()),
//            )
//        }
//    }
//}


//-----------Preview --------------
@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun NoteItemPreview() {
    MaterialTheme {
        Surface {
            // NoteItem(note = Note(id = 0, title = "Title ", content = "Content", color = Note.noteColors[4].toArgb(), timestamp = 0)) { }
        }
    }
}