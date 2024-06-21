package com.ighorosipov.multitimer.presentation.ui.components.bottom_navigation

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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.ighorosipov.multitimer.R
import com.ighorosipov.multitimer.presentation.screens.State
import com.ighorosipov.multitimer.presentation.ui.components.navigation.NavigationEvent
import com.ighorosipov.multitimer.presentation.ui.components.navigation.Screen

@Composable
fun MainBottomNavigation(
    navController: NavController,
    showLabel: Boolean = true,
    state: State,
) {
    var selectedItemIndex by rememberSaveable {
        mutableIntStateOf(0)
    }
    selectedItemIndex = when (state.navigationEvent) {
        is NavigationEvent.NavigateWithDeeplink -> {
            state.navigationEvent.screen.navBarPosition ?: selectedItemIndex
        }

        is NavigationEvent.None -> selectedItemIndex
    }

    NavigationBar(modifier = Modifier.statusBarsPadding()) {
        val items = listOf(
            BottomNavigationItem(
                title = stringResource(R.string.alarm),
                route = Screen.AlarmScreen.route,
                selectedIcon = Icons.Filled.Alarm,
                unselectedIcon = Icons.Outlined.Alarm,
                hasNotification = false,
            ),
            BottomNavigationItem(
                title = stringResource(R.string.world_time_short),
                route = Screen.WorldTimeScreen.route,
                selectedIcon = Icons.Filled.CameraAlt,
                unselectedIcon = Icons.Outlined.CameraAlt,
                hasNotification = false,
            ),
            BottomNavigationItem(
                title = stringResource(R.string.stopwatch),
                route = Screen.StopwatchScreen.route,
                selectedIcon = Icons.Filled.Timer,
                unselectedIcon = Icons.Outlined.Timer,
                hasNotification = false,
            ),
            BottomNavigationItem(
                title = stringResource(R.string.timer),
                route = Screen.TimerScreen.route,
                selectedIcon = Icons.Filled.AvTimer,
                unselectedIcon = Icons.Outlined.AvTimer,
                hasNotification = false,
            )
        )
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedItemIndex == index,
                onClick = {
                    selectedItemIndex = index
                    navController.navigate(item.route)
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
                            imageVector = if (index == selectedItemIndex) {
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