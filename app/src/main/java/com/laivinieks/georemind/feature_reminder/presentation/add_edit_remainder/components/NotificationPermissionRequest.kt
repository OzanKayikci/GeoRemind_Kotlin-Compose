package com.laivinieks.georemind.feature_reminder.presentation.add_edit_remainder.components

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.Settings
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.laivinieks.georemind.core.data.data_source.UserPreferencesDataStore
import com.laivinieks.georemind.core.domain.util.Constants
import kotlinx.coroutines.launch

@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun NotificationPermissionRequest(
    isGranted: (isGranted: Boolean) -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val dataStore = UserPreferencesDataStore(context)

    val permissionDeniedTwice = dataStore.getNotificationPermissionIsDenied.collectAsState(initial = false)

    val notificationPermissionState = rememberPermissionState(permission = Manifest.permission.POST_NOTIFICATIONS)

    var permissionStep by remember {
        mutableStateOf(PermissionStep.PERMISSION_QUEST)
    }

    if (notificationPermissionState.status.isGranted) {
        scope.launch {
            dataStore.storePermissionIsDenied(false, Constants.KEY_PERMISSION_LOCATION)
        }
        isGranted(true)
    } else {
        AlertDialog(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(size = 16.dp)
                ),
            onDismissRequest = { isGranted(false) })
        {


            if (permissionDeniedTwice.value) {
                permissionStep = PermissionStep.PERMANENTLY_DENIED

            } else if (notificationPermissionState.status.shouldShowRationale) {

                permissionStep = PermissionStep.RATIONALE_DIALOG

            }

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
                        text = permissionStep.title,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Black,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                    )
                    Divider(modifier = Modifier.padding(vertical = 16.dp), color = MaterialTheme.colorScheme.onPrimaryContainer)
                    Text(
                        text = permissionStep.description,
                        textAlign = TextAlign.Justify,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
                        onClick = {
                            if (notificationPermissionState.status.shouldShowRationale) {
                                scope.launch {
                                    dataStore.storePermissionIsDenied(true, Constants.KEY_PERMISSION_LOCATION)
                                }
                            }
                            if (!permissionDeniedTwice.value) {

                                notificationPermissionState.launchPermissionRequest()
                            } else {

                                com.laivinieks.georemind.feature_reminder.presentation.add_edit_remainder.components.location.goToAppSetting(context as Activity)
                            }
                        }) {
                        Text(
                            text = permissionStep.buttonText,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }

//                if ( !locationPermissionsState.allPermissionsGranted) {
//                    Button(onClick = { goToAppSetting(activity = activity) }) {
//                        Text("Go to app settings")
//                    }
//
//                } else {

//                }
                }
            }

        }

    }
}

enum class PermissionStep(
    val title: String,
    val description: String,
    val buttonText: String
) {
    PERMISSION_QUEST(
        title = "Notification Permission",
        description = "The app needs this permission to send reminder notifications even when the app is closed.",
        buttonText = "Request Permissions"
    ),

    RATIONALE_DIALOG(
        title = "Notification Permission",
        description = " \"The app needs this permission to send reminder notifications even when the app is closed. Please grant us to send notification. \n" +
                "Otherwise you will not receive a reminder notification",
        buttonText = "Request Permissions"
    ),
    PERMANENTLY_DENIED(
        title = "Location Permission",
        description = "The app needs this permission to send reminder notifications even when the app is closed. Otherwise you will not receive a reminder notification." +
                "You can grant access in the application settings.",
        buttonText = "Go to App Settings"
    ),

}

fun goToAppSetting(activity: Activity) {
    val i = Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", activity.packageName, null)
    )
    activity.startActivity(i)
}

