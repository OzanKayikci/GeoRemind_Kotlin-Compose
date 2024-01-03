package com.laivinieks.georemind.feature_reminder.presentation.add_edit_remainder.components.time

import android.app.Activity
import android.app.AlarmManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.provider.Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExactAlarmPermission(
    context: Context,
    alarmManager: AlarmManager,
    permissionApproved: (Boolean) -> Unit
) {
    Log.d("in alarm","alalramrrm")
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        if (!alarmManager.canScheduleExactAlarms()) {

            val descText = buildAnnotatedString {
                append("We need this permission to set alarm for your reminder. \nPlease open ")
                withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary, textDecoration = TextDecoration.Underline)) {
                    append("'Allow settings alarms and reminders'")
                }
                append(" in the Alarm & reminders setting in the app settings ")


            }
            AlertDialog(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colorScheme.surface,
                        shape = RoundedCornerShape(size = 16.dp)
                    ),
                onDismissRequest = { permissionApproved(false)})
            {


                Box(
                    modifier = Modifier

                        .background(
                            shape = RoundedCornerShape(16.dp),
                            color = MaterialTheme.colorScheme.background.copy(alpha = 0.6f)
                        )
                        .border(4.dp, Color.Black, RoundedCornerShape(16.dp))

                )
                {
                    Column(
                        modifier = Modifier.padding(26.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {


                        Text(
                            text = "Alarm Permission",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Black,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                        )
                        Divider(modifier = Modifier.padding(vertical = 16.dp), color = MaterialTheme.colorScheme.onPrimaryContainer)
                        Text(
                            text = descText,
                            textAlign = TextAlign.Justify,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
                            onClick = {

                                goToAppSetting(context as Activity)

                            }) {
                            Text(
                                text = "Open App Settings",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    }
                }
            }
        } else {
            permissionApproved(true)
        }

    } else {
        permissionApproved(true)
    }
}

@RequiresApi(Build.VERSION_CODES.S)
fun goToAppSetting(activity: Activity) {
    activity.startActivity(
        Intent(
            Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM,
            Uri.fromParts("package", activity.packageName, null)

        )
    )
}