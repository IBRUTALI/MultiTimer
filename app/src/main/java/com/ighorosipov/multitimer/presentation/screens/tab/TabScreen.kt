package com.ighorosipov.multitimer.presentation.screens.tab

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ighorosipov.multitimer.presentation.screens.State
import com.ighorosipov.multitimer.presentation.ui.components.bottom_navigation.MainBottomNavigation
import com.ighorosipov.multitimer.presentation.ui.components.navigation.TabNavGraph

@Composable
fun TabScreen(
    navController: NavHostController = rememberNavController(),
    state: State
) {
    Scaffold(
        topBar = {  },
        bottomBar = { MainBottomNavigation(
            navController = navController,
            state = state
        ) }
    ) { paddingValues ->
        TabNavGraph(
            modifier = Modifier.padding(paddingValues),
            navController = navController,
            state = state
        )
    }
}