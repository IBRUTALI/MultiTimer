package com.ighorosipov.multitimer.ui.components.navigation.tab_nav_graphs

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ighorosipov.multitimer.feature.stopwatch.StopwatchScreen
import com.ighorosipov.multitimer.ui.components.navigation.Screen

@Composable
fun StopwatchScreenNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController= rememberNavController()
) {
    NavHost(
        navController = navController,
        route = Screen.StopwatchGraph().route,
        startDestination = Screen.StopwatchGraph.Stopwatch().route
    ) {
        composable(
            route = Screen.StopwatchGraph.Stopwatch().route
        ) {
            StopwatchScreen(
                modifier = modifier,
                navController = navController
            )
        }
    }
}