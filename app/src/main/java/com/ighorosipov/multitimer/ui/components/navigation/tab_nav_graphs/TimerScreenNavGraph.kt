package com.ighorosipov.multitimer.ui.components.navigation.tab_nav_graphs

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink
import com.ighorosipov.multitimer.feature.timer.presentation.screens.add_timer.AddTimerScreen
import com.ighorosipov.multitimer.feature.timer.presentation.screens.timer.TimerScreen
import com.ighorosipov.multitimer.feature.timer.presentation.screens.timer_details.TimerDetailsScreen
import com.ighorosipov.multitimer.ui.State
import com.ighorosipov.multitimer.ui.components.navigation.Screen

@Composable
fun TimerScreenNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    state: State,
) {
    NavHost(
        navController = navController,
        route = Screen.TimerGraph().route,
        startDestination = Screen.TimerGraph.Timer().route
    ) {
        composable(
            route = Screen.TimerGraph.Timer().route
        ) {
            TimerScreen(
                navController = navController,
                modifier = modifier
            )
        }
        composable(route = Screen.TimerGraph.AddTimer().route) {
            AddTimerScreen(
                navController = navController,
                modifier = modifier
            )
        }
        composable(
            route = Screen.TimerGraph.TimerDetails().route,
            deepLinks = listOf(
                navDeepLink {
                    uriPattern = Screen.TimerGraph.TimerDetails().deeplink.toString()
                    action = Intent.ACTION_VIEW
                }
            )
        ) {
            TimerDetailsScreen(
                navController = navController,
                modifier = modifier
            )
        }
    }

}