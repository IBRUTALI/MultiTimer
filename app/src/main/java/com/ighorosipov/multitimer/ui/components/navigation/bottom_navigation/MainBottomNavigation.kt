package com.ighorosipov.multitimer.ui.components.navigation.bottom_navigation

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.ighorosipov.multitimer.R
import com.ighorosipov.multitimer.ui.State
import com.ighorosipov.multitimer.ui.components.navigation.Screen
import com.ighorosipov.multitimer.ui.theme.Grey
import com.ighorosipov.multitimer.ui.theme.Typography

@Composable
fun MainBottomNavigation(
    navController: NavController,
    showLabel: Boolean = true,
    state: State,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar(
        modifier = Modifier
            .statusBarsPadding()
            .height(60.dp),
        containerColor = MaterialTheme.colorScheme.secondary
    ) {
        val items = listOf(
            BottomNavigationItem(
                title = stringResource(R.string.alarm),
                route = Screen.AlarmGraph().route,
                selectedIcon = ImageVector.vectorResource(R.drawable.ic_alarm_fill),
                unselectedIcon = ImageVector.vectorResource(R.drawable.ic_alarm_outline),
                hasNotification = false,
            ),
            BottomNavigationItem(
                title = stringResource(R.string.world_time_short),
                route = Screen.WorldTimeGraph().route,
                selectedIcon = ImageVector.vectorResource(R.drawable.ic_earth_fill),
                unselectedIcon = ImageVector.vectorResource(R.drawable.ic_earth_outline),
                hasNotification = false,
            ),
            BottomNavigationItem(
                title = stringResource(R.string.stopwatch),
                route = Screen.StopwatchGraph().route,
                selectedIcon = ImageVector.vectorResource(R.drawable.ic_stopwatch_fill),
                unselectedIcon = ImageVector.vectorResource(R.drawable.ic_stopwatch_outline),
                hasNotification = false,
            ),
            BottomNavigationItem(
                title = stringResource(R.string.timer),
                route = Screen.TimerGraph().route,
                selectedIcon = ImageVector.vectorResource(R.drawable.ic_timer_fill),
                unselectedIcon = ImageVector.vectorResource(R.drawable.ic_timer_outline),
                hasNotification = false,
            )
        )
        items.forEach { item ->
            val isCurrentDestination: Boolean = currentDestination?.hierarchy?.any {
                it.route == item.route
            } == true
            NavigationBarItem(
                modifier = Modifier.padding(0.dp),
                colors = androidx.compose.material3.NavigationBarItemDefaults
                    .colors(
                        indicatorColor = MaterialTheme.colorScheme.secondary
                    ),
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
                    if (showLabel) Text(
                        text = item.title,
                        style = Typography.bodySmall,
                        color = if (!isCurrentDestination) {
                            Grey
                        } else {
                            MaterialTheme.colorScheme.onPrimary
                        }
                    )
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
                            tint = if (isCurrentDestination) {
                                MaterialTheme.colorScheme.onSurface
                            } else {
                                Grey
                            },
                            contentDescription = item.title
                        )
                    }
                }
            )
        }
    }
}