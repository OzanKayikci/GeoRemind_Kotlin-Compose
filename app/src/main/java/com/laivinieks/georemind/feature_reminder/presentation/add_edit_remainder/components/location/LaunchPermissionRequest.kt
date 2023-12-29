package com.laivinieks.georemind.feature_reminder.presentation.add_edit_remainder.components.location

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
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
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp

import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.rememberPermissionState
import com.laivinieks.georemind.core.data.data_source.UserPreferencesDataStore
import com.laivinieks.georemind.core.domain.util.Constants

import kotlinx.coroutines.launch

@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun LaunchPermissionRequest(
    isGranted: (isGranted: Boolean) -> Unit
) {

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val dataStore = UserPreferencesDataStore(context)

    val permissionDeniedTwice = dataStore.getLocationPermissionIsDenied.collectAsState(initial = false)

    val backgroundLocationPermissionsState = rememberPermissionState(permission = android.Manifest.permission.ACCESS_BACKGROUND_LOCATION)
    val locationPermissionsState = rememberMultiplePermissionsState(
        listOf(
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION,
        )
    )

    var permissionStep by remember {
        mutableStateOf(PermissionStep.PERMISSION_QUEST)
    }


    if (locationPermissionsState.allPermissionsGranted && backgroundLocationPermissionsState.status.isGranted) {
        scope.launch {
            dataStore.storePermissionIsDenied(false, Constants.KEY_PERMISSION_LOCATION)
        }
        isGranted(true)
    } else {

        var onlyBackgroundWaiting = false

        val allPermissionsRevoked =
            locationPermissionsState.permissions.size ==
                    locationPermissionsState.revokedPermissions.size
        if (!allPermissionsRevoked) {
            permissionStep = PermissionStep.FINE_LOCATION_QUEST
        } else {
            if (permissionDeniedTwice.value) {
                permissionStep = PermissionStep.PERMANENTLY_DENIED

            } else if (locationPermissionsState.shouldShowRationale) {

                permissionStep = PermissionStep.RATIONALE_DIALOG

            }
        }
        if (locationPermissionsState.allPermissionsGranted && !backgroundLocationPermissionsState.status.isGranted) {
            permissionStep = PermissionStep.BACKGROUND_LOCATION_QUEST
            onlyBackgroundWaiting = true
        }

        val descText = buildAnnotatedString {
            append(permissionStep.description)
            if (onlyBackgroundWaiting) {
                withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary, textDecoration = TextDecoration.Underline)) {
                    append("'Allow all the time'")
                }
                append(" checkbox in the location permissions in the app permissions in the app settings ")
            }

        }

        AlertDialog(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(size = 16.dp)
                ),
            onDismissRequest = { isGranted(onlyBackgroundWaiting) })
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
                        text = permissionStep.title,
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
                            if (onlyBackgroundWaiting) {
                                goToAppSetting(context as Activity)
                            } else {
                                if (locationPermissionsState.shouldShowRationale) {
                                    scope.launch {
                                        dataStore.storePermissionIsDenied(true, Constants.KEY_PERMISSION_LOCATION)
                                    }
                                }
                                if (!permissionDeniedTwice.value) {
                                    locationPermissionsState.launchMultiplePermissionRequest()

                                } else {


                                }
                            }

                        }) {
                        Text(
                            fontWeight = FontWeight.SemiBold,
                            text = permissionStep.buttonText,
                            style = MaterialTheme.typography.titleMedium,
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
        title = "Location Permission",
        description = "Getting your location is important for this feature.",
        buttonText = "Request Permissions"
    ),
    FINE_LOCATION_QUEST(
        title = "Fine Location Permission",
        description = "Thanks for letting app access your approximate location.\n But The feature needs fine location access to work properly.",
        buttonText = "Allow Precise Location"
    ),
    RATIONALE_DIALOG(
        title = "Location Permission",
        description = " \"The app need your exact location is important for this feature. Please grant us fine location \n" +
                "Otherwise you can't use this feature",
        buttonText = "Request Permissions"
    ),
    PERMANENTLY_DENIED(
        title = "Location Permission",
        description = "The app need your exact location is important for this feature. \n" +
                "You can grant access in the application settings.",
        buttonText = "Go to App Settings"
    ),
    BACKGROUND_LOCATION_QUEST(
        title = "Background Location Permission",
        description = "Everything is perfect. Now you can use the map.\n" +
                "However, if you want location-based reminders, you need to check the  ",
        buttonText = "Go to App Settings"
    )

}

fun goToAppSetting(activity: Activity) {
    val i = Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", activity.packageName, null)
    )
    activity.startActivity(i)
}