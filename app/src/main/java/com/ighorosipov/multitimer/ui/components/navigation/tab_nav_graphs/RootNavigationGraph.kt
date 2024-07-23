package com.ighorosipov.multitimer.ui.components.navigation.tab_nav_graphs

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ighorosipov.multitimer.feature.tab.TabScreen
import com.ighorosipov.multitimer.ui.State

@Composable
fun RootNavigationGraph(
    navController: NavHostController,
    state: State
) {
    NavHost(
        navController = navController,
        route = Graph.ROOT,
        startDestination = Graph.TAB
    ) {
        composable(
            route = Graph.TAB
        ) {
            TabScreen(
                navController = rememberNavController(),
                state = state
            )
        }
    }
}

object Graph {
    const val ROOT = "root_graph"
    const val TAB = "tab_graph"
}