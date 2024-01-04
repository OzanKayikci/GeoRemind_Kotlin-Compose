package com.laivinieks.georemind.feature_note.presentation.notes


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun DefaultRadioButton(
    text: String,
    selected: Boolean,
    onSelect: () -> Unit,
    modifier: Modifier = Modifier
) {

    Row(
        modifier = modifier.padding(16.dp,8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        RadioButton(
            modifier = Modifier.size(20.dp)
            ,
            selected = selected, onClick = onSelect, colors = RadioButtonDefaults.colors(
                selectedColor = MaterialTheme.colorScheme.onTertiaryContainer,
                unselectedColor = MaterialTheme.colorScheme.onBackground
            ),
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = text,
            color = if (selected) MaterialTheme.colorScheme.onTertiaryContainer else MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview("radio", showSystemUi = true, showBackground = true)
@Composable
private fun DefRadioBtnPreview() {
    MaterialTheme {
        Surface {
            DefaultRadioButton(text = "Date", selected = false, onSelect = {  })

        }
    }
}