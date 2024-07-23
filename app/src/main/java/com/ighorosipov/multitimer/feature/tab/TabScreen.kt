package com.ighorosipov.multitimer.feature.tab

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.ighorosipov.multitimer.ui.State
import com.ighorosipov.multitimer.ui.components.navigation.bottom_navigation.MainBottomNavigation
import com.ighorosipov.multitimer.ui.components.navigation.tab_nav_graphs.TabNavGraph

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TabScreen(
    navController: NavHostController,
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