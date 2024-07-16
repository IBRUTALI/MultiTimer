package com.ighorosipov.multitimer.feature.tab

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ighorosipov.multitimer.ui.State
import com.ighorosipov.multitimer.ui.components.navigation.Screen
import com.ighorosipov.multitimer.ui.components.navigation.TabNavGraph
import com.ighorosipov.multitimer.ui.components.navigation.bottom_navigation.MainBottomNavigation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TabScreen(
    navController: NavHostController = rememberNavController(),
    state: State
) {
    val context = LocalContext.current
    Scaffold(
        topBar = { TopAppBar(title = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination?.route
            val title = when(currentDestination) {
                 Screen.Alarm().route -> context.resources.getString(
                     Screen.Alarm().labelStringId
                 )
                Screen.WorldTime().route -> context.resources.getString(
                    Screen.WorldTime().labelStringId
                )
                Screen.Stopwatch().route -> context.resources.getString(
                    Screen.Stopwatch().labelStringId
                )
                Screen.Timer().route -> context.resources.getString(
                    Screen.Timer().labelStringId
                )
                Screen.AddTimer().route -> context.resources.getString(
                    Screen.AddTimer().labelStringId
                )
                Screen.TimerDetails().route -> context.resources.getString(
                    Screen.TimerDetails().labelStringId
                )
                else -> null
            }
            title?.let { Text(it) }
        }) },
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