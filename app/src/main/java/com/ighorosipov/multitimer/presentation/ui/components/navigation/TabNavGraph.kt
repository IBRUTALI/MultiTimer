package com.ighorosipov.multitimer.presentation.ui.components.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ighorosipov.multitimer.presentation.screens.alarm.AlarmScreen
import com.ighorosipov.multitimer.presentation.screens.stopwatch.StopwatchScreen
import com.ighorosipov.multitimer.presentation.screens.timer.TimerScreen
import com.ighorosipov.multitimer.presentation.screens.world_time.WorldTimeScreen

@Composable
fun TabNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
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
        composable(route = Screen.TimerScreen.route) {
            TimerScreen(modifier = modifier)
        }
    }
}