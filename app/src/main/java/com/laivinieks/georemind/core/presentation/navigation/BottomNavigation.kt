package com.laivinieks.georemind.core.presentation.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.laivinieks.georemind.core.util.Screen

@Composable
fun GeoRemindBottomBar(
    navHostController: NavHostController
) {
    val screens = listOf(
        Screen.RemainderScreen,
        Screen.NotesScreen
    )
    val navStackBackEntry by navHostController.currentBackStackEntryAsState()
    val currentDestination = navStackBackEntry?.destination
    Row(
        modifier = Modifier
            .padding(bottom =25.dp)
            .background(MaterialTheme.colorScheme.background)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        screens.forEach { screen ->
            AddItem(screen = screen, currentDestination = currentDestination, navHostController = navHostController)
        }
    }
//    NavigationBar {
//        screens.forEachIndexed { index, screen ->
//            NavigationBarItem(
//                selected = selectedItem == index,
//                onClick = {
//                    selectedItem = index
//                    navHostController.navigate(screen.route) {
//                        popUpTo(navHostController.graph.startDestinationId)
//                        launchSingleTop = true
//                    }
//                },
//                icon = {
//                    Icon(
//                        imageVector = screen.icon,
//                        contentDescription = screen.title
//                    )
//                },
//                label = {
//                    Text(text = screen.title)
//                },
//
//                )
//
//        }
//    }
}

@Composable
fun AddItem(
    screen: Screen,
    currentDestination: NavDestination?,
    navHostController: NavHostController
) {
    val selected = currentDestination?.hierarchy?.any {
        it.route == screen.route
    } == true

    val background = if (selected) MaterialTheme.colorScheme.primary else Color.Transparent

    val contentColor = if (selected) Color.White else MaterialTheme.colorScheme.onPrimaryContainer
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(background)
            .height(40.dp)
            .clickable {
                navHostController.navigate(screen.route) {
                    popUpTo(navHostController.graph.findStartDestination().id)
                    launchSingleTop = true
                }
            }
    ) {
        Row(
            modifier = Modifier
                .padding(10.dp, 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                painter = painterResource(id = if (selected) screen.iconsFocused else screen.icon),
                contentDescription = screen.title,
                tint = contentColor
            )
            AnimatedVisibility(visible = selected) {
                Text(text = screen.title, color = contentColor)
            }

        }
    }
}