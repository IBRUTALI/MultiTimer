package com.ighorosipov.multitimer.ui.components.navigation

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import com.ighorosipov.multitimer.feature.alarm.AlarmScreen
import com.ighorosipov.multitimer.feature.stopwatch.StopwatchScreen
import com.ighorosipov.multitimer.feature.timer.presentation.screens.timer.TimerScreen
import com.ighorosipov.multitimer.feature.world_time.WorldTimeScreen
import com.ighorosipov.multitimer.ui.State

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
        startDestination = Screen.Alarm().route
    ) {
        composable(route = Screen.Alarm().route) {
            AlarmScreen(modifier = modifier)
        }
        composable(route = Screen.WorldTime().route) {
            WorldTimeScreen(modifier = modifier)
        }
        composable(route = Screen.Stopwatch().route) {
            StopwatchScreen(modifier = modifier)
        }
        composable(
            route = Screen.Timer().route,
            deepLinks = listOf(
                navDeepLink {
                    uriPattern = Screen.Timer().deeplink.toString()
                    action = Intent.ACTION_VIEW
                }
            )
        ) {
            TimerScreen(
                navController = navController,
                modifier = modifier
            )
        }
    }
}