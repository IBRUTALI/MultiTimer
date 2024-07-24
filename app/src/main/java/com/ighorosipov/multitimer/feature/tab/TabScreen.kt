package com.ighorosipov.multitimer.feature.tab

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ighorosipov.multitimer.ui.State
import com.ighorosipov.multitimer.ui.components.navigation.bottom_navigation.MainBottomNavigation
import com.ighorosipov.multitimer.ui.components.navigation.tab_nav_graphs.TabNavGraph

@Composable
fun TabScreen(
    navController: NavHostController = rememberNavController(),
    state: State,
) {
    Scaffold(
        bottomBar = {
            MainBottomNavigation(
                navController = navController,
                state = state
            )
        }
    ) { paddingValues ->
        TabNavGraph(
            modifier = Modifier.padding(paddingValues),
            navController = navController,
            state = state
        )
    }
}