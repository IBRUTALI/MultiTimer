package com.ighorosipov.multitimer.ui.components.navigation.tab_nav_graphs

import android.content.Intent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import com.ighorosipov.multitimer.ui.components.navigation.Screen

@Composable
fun TimerScreenNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        route = Screen.TimerGraph().route,
        startDestination = Screen.TimerGraph.Timer().route,
        enterTransition = {
            fadeIn()
        },
        exitTransition = {
            fadeOut()
        }
    ) {
        composable(route = Screen.TimerGraph.Timer().route) {
            TimerScreen(
                modifier = modifier,
                navController = navController
            )
        }
        composable(route = Screen.TimerGraph.AddTimer().route) {
            AddTimerScreen(
                modifier = modifier,
                navController = navController
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
                modifier = modifier,
                navController = navController
            )
        }
    }
}