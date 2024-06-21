package com.ighorosipov.multitimer.presentation.ui.components.navigation

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import com.ighorosipov.multitimer.presentation.screens.State
import com.ighorosipov.multitimer.presentation.screens.alarm.AlarmScreen
import com.ighorosipov.multitimer.presentation.screens.stopwatch.StopwatchScreen
import com.ighorosipov.multitimer.presentation.screens.timer.TimerScreen
import com.ighorosipov.multitimer.presentation.screens.world_time.WorldTimeScreen

@Composable
fun TabNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    state: State
) {

    LaunchedEffect(state) {
        when (state.navigationEvent) {
            is NavigationEvent.NavigateWithDeeplink -> {
                navController.navigate(state.navigationEvent.screen.deeplink)
            }
            NavigationEvent.None -> Unit
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
                    uriPattern = Screen.TimerScreen.deeplink.toString()
                    action = Intent.ACTION_VIEW
                }
            )
        ) {
            TimerScreen(modifier = modifier)
        }
    }
}