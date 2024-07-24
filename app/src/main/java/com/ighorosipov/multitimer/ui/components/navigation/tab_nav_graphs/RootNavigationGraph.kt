package com.ighorosipov.multitimer.ui.components.navigation.tab_nav_graphs

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ighorosipov.multitimer.feature.tab.TabScreen
import com.ighorosipov.multitimer.ui.State
import com.ighorosipov.multitimer.ui.components.navigation.Screen

@Composable
fun RootNavigationGraph(
    navController: NavHostController,
    state: State,
) {
    NavHost(
        navController = navController,
        route = Screen.RootGraph.route,
        startDestination = Screen.TabGraph.route
    ) {
        composable(
            route = Screen.TabGraph.route
        ) {
            TabScreen(
                state = state
            )
        }
    }
}