package com.ighorosipov.multitimer.presentation.ui.components.navigation

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import com.ighorosipov.multitimer.presentation.Event
import com.ighorosipov.multitimer.presentation.screens.alarm.AlarmScreen
import com.ighorosipov.multitimer.presentation.screens.stopwatch.StopwatchScreen
import com.ighorosipov.multitimer.presentation.screens.timer.TimerScreen
import com.ighorosipov.multitimer.presentation.screens.world_time.WorldTimeScreen

@Composable
fun TabNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    event: Event
) {

    LaunchedEffect(event) {
        when (event) {
            is Event.NavigateWithDeeplink -> {
                navController.navigate(event.deeplink)
            }
            Event.None -> Unit
        }
    }

    NavHost(
        navController = navController,
        route = Graph.TAB,
        startDestination = Screen.AlarmScreen.route
    ) {
        composable(route = Screen.AlarmScreen.route) {
            AlarmScreen(modifier = modifier)
        }
        composable(route = Screen.WorldTimeScreen.route) {
            WorldTimeScreen(modifier = modifier)
        }
        composable(route = Screen.StopwatchScreen.route) {
            StopwatchScreen(modifier = modifier)
        }
        composable(
            route = Screen.TimerScreen.route,
            deepLinks = listOf(
                navDeepLink {
                    uriPattern = Screen.TIMER_SCREEN_DEEP_LINK
                    action = Intent.ACTION_VIEW
                }
            )
        ) {
            TimerScreen(modifier = modifier)
        }
    }
}