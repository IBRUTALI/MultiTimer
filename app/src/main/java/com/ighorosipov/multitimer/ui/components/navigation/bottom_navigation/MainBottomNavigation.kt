package com.ighorosipov.multitimer.ui.components.navigation.bottom_navigation

import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.AvTimer
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.outlined.Alarm
import androidx.compose.material.icons.outlined.AvTimer
import androidx.compose.material.icons.outlined.CameraAlt
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.ighorosipov.multitimer.R
import com.ighorosipov.multitimer.ui.State
import com.ighorosipov.multitimer.ui.components.navigation.Screen

@Composable
fun MainBottomNavigation(
    navController: NavController,
    showLabel: Boolean = true,
    state: State
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar(modifier = Modifier.statusBarsPadding()) {
        val items = listOf(
            BottomNavigationItem(
                title = stringResource(R.string.alarm),
                route = Screen.AlarmGraph().route,
                selectedIcon = Icons.Filled.Alarm,
                unselectedIcon = Icons.Outlined.Alarm,
                hasNotification = false,
            ),
            BottomNavigationItem(
                title = stringResource(R.string.world_time_short),
                route = Screen.WorldTimeGraph().route,
                selectedIcon = Icons.Filled.CameraAlt,
                unselectedIcon = Icons.Outlined.CameraAlt,
                hasNotification = false,
            ),
            BottomNavigationItem(
                title = stringResource(R.string.stopwatch),
                route = Screen.StopwatchGraph().route,
                selectedIcon = Icons.Filled.Timer,
                unselectedIcon = Icons.Outlined.Timer,
                hasNotification = false,
            ),
            BottomNavigationItem(
                title = stringResource(R.string.timer),
                route = Screen.TimerGraph().route,
                selectedIcon = Icons.Filled.AvTimer,
                unselectedIcon = Icons.Outlined.AvTimer,
                hasNotification = false,
            )
        )
        items.forEachIndexed { index, item ->
            val isCurrentDestination: Boolean = currentDestination?.hierarchy?.any {
                it.route == item.route
            } == true
            NavigationBarItem(
                selected = isCurrentDestination,
                onClick = {
                    navController.navigate(item.route) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                },
                label = {
                    if (showLabel) Text(text = item.title)
                },
                icon = {
                    BadgedBox(
                        badge = {
                            if (item.hasNotification) {
                                Badge()
                            }
                        }
                    ) {
                        Icon(
                            imageVector = if (isCurrentDestination) {
                                item.selectedIcon
                            } else {
                                item.unselectedIcon
                            },
                            contentDescription = item.title
                        )
                    }
                }
            )
        }
    }
}