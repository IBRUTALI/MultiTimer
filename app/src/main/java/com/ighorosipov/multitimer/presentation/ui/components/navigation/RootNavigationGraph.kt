package com.ighorosipov.multitimer.presentation.ui.components.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ighorosipov.multitimer.presentation.screens.tab.TabScreen

@Composable
fun RootNavigationGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        route = Graph.ROOT,
        startDestination = Graph.TAB
    ) {
        composable(route = Graph.TAB) {
            TabScreen()
        }
    }
}

object Graph {
    const val ROOT = "root_graph"
    const val TAB = "tab_graph"
}