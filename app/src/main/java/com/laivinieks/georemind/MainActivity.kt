package com.laivinieks.georemind

import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier

import androidx.navigation.compose.rememberNavController
import com.laivinieks.georemind.core.presentation.navigation.GeoRemindBottomBar
import com.laivinieks.georemind.core.presentation.navigation.GeoRemindNavHost
import com.laivinieks.georemind.feature_geofence.presentation.GeofenceBroadcastReceiver
import com.laivinieks.georemind.ui.theme.GeoRemindTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val geofenceReceiver = GeofenceBroadcastReceiver()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.auto(Color.TRANSPARENT, Color.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.auto(Color.TRANSPARENT, Color.TRANSPARENT)
        )
        setContent {
            GeoRemindTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    Scaffold(bottomBar = {
                        GeoRemindBottomBar(navHostController = navController)
                    },
                        ) {
                        GeoRemindNavHost(
                            navController = navController,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(it)
                        )
                    }

                }
            }
        }
    }
}