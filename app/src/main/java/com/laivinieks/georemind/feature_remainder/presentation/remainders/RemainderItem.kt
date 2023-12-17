package com.laivinieks.georemind.feature_remainder.presentation.remainders

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.laivinieks.georemind.feature_note.domain.modal.Note

@Composable
fun RemainderItem(
    note: Note,
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 16.dp,
    onDeleteClick: () -> Unit
) {
Column (modifier = modifier)
{

}
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun RemainderItemPreview(){
    RemainderItem(note = Note(id = null, title = "Title", content = "Content", timestamp = 0, color = 0)) {

    }
}



