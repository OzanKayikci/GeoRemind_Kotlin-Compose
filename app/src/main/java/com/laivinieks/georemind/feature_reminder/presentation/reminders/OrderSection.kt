package com.laivinieks.georemind.feature_reminder.presentation.reminders

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import com.laivinieks.georemind.core.domain.util.OrderType
import com.laivinieks.georemind.feature_note.presentation.notes.DefaultRadioButton
import com.laivinieks.georemind.feature_reminder.domain.util.ReminderOrder

@Composable
fun OrderSection(
    modifier: Modifier,
    reminderOrder: ReminderOrder = ReminderOrder.Date(OrderType.Descending),
    onOrderChange: (ReminderOrder) -> Unit
) {

    Column(modifier = Modifier.fillMaxWidth(0.9f), horizontalAlignment = Alignment.CenterHorizontally) {

        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween) {

            DefaultRadioButton(

                text = "Title",
                selected = reminderOrder is ReminderOrder.Title,
                onSelect = { onOrderChange(ReminderOrder.Title(reminderOrder.orderType)) })



            DefaultRadioButton(
                text = "Date",
                selected = reminderOrder is ReminderOrder.Date,
                onSelect = { onOrderChange(ReminderOrder.Date(reminderOrder.orderType)) })



            DefaultRadioButton(
                text = "Color",
                selected = reminderOrder is ReminderOrder.Color,
                onSelect = { onOrderChange(ReminderOrder.Color(reminderOrder.orderType)) })

        }
        Row(modifier = Modifier.fillMaxWidth().padding(0.dp), horizontalArrangement = Arrangement.SpaceEvenly) {

            DefaultRadioButton(
                text = "Location",
                selected = reminderOrder is ReminderOrder.Location,
                onSelect = { onOrderChange(ReminderOrder.Location(reminderOrder.orderType)) })


            DefaultRadioButton(
                text = "Reminder Time",
                selected = reminderOrder is ReminderOrder.ReminderTime,
                onSelect = { onOrderChange(ReminderOrder.ReminderTime(reminderOrder.orderType)) })
        }
        Divider(modifier = Modifier.fillMaxWidth(0.93f).padding(vertical = 8.dp))

        Row(modifier = Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.Center) {
            DefaultRadioButton(
                text = "Ascending",
                selected = reminderOrder.orderType is OrderType.Ascending,
                onSelect = { onOrderChange(reminderOrder.copy(OrderType.Ascending)) })

            Spacer(modifier = Modifier.width(8.dp))

            DefaultRadioButton(
                text = "Descending",
                selected = reminderOrder.orderType is OrderType.Descending,
                onSelect = { onOrderChange(reminderOrder.copy(OrderType.Descending)) })
        }

    }
}