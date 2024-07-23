package com.ighorosipov.multitimer.ui.components.navigation.tab_nav_graphs

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ighorosipov.multitimer.feature.alarm.AlarmScreen
import com.ighorosipov.multitimer.ui.components.navigation.Screen

@Composable
fun AlarmScreenNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        route = Screen.AlarmGraph().route,
        startDestination = Screen.AlarmGraph.Alarm().route
    ) {
        composable(
            route = Screen.AlarmGraph.Alarm().route
        ) {
            AlarmScreen(
                navController = navController,
                modifier = modifier
            )
        }
    }
}