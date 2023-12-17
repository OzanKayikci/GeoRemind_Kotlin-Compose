package com.laivinieks.georemind.feature_reminder.presentation.remainders

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.laivinieks.georemind.R
import com.laivinieks.georemind.feature_note.domain.modal.Note
import com.laivinieks.georemind.core.domain.util.Converters
import com.laivinieks.georemind.ui.theme.LocalCustomColorsPalette
import com.laivinieks.georemind.ui.theme.iterateOverNoteColors

@Composable
fun RemainderItem(
    note: Note,
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 10.dp,
    onDeleteClick: () -> Unit
) {
    val colorRatio = if (isSystemInDarkTheme()) 0.2f else 0.4f
    val noteColor = iterateOverNoteColors(LocalCustomColorsPalette.current)[note.color].toArgb()
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(cornerRadius)),

        ) {

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .background(Converters.getSecondaryColor(Color(noteColor), colorRatio))
                .padding(vertical = 8.dp, horizontal = 12.dp)

        )
        {

            Text(
                text = note.title,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                maxLines = 1,
                modifier = Modifier.fillMaxWidth(),
                overflow = TextOverflow.Ellipsis,

                )


            Divider(color = Color(noteColor), thickness = 0.25.dp, modifier = Modifier.padding(0.dp, 4.dp))


            Text(
                text = note.content,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium),
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier,
                maxLines = 6,
                overflow = TextOverflow.Ellipsis,
            )
        }
        Box(
            modifier = Modifier

                .height(30.dp)
                .background(Color(noteColor)) // Change the background color as needed
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_location_filled),
                    contentDescription = "location",
                    tint = Color.White,
                    modifier = Modifier
                        .size(20.dp)
                )
                Text(
                    text = "Your Location",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White,
                    fontWeight = FontWeight.ExtraBold,
                    maxLines = 1,
                    modifier = Modifier
                        .padding(horizontal = 6.dp),
                    overflow = TextOverflow.Ellipsis,

                    )
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun RemainderItemPreview() {
    RemainderItem(note = Note(id = null, title = "Title", content = "Content", timestamp = 0, color = 1)) {

    }
}



