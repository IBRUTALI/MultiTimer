package com.ighorosipov.multitimer.ui.components.navigation.tab_nav_graphs

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.ighorosipov.multitimer.ui.State
import com.ighorosipov.multitimer.ui.components.navigation.Screen

@Composable
fun TabNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    state: State,
) {

//    LaunchedEffect(state) {
//        when (state.navigationEvent) {
//            is NavigationEvent.NavigateWithDeeplink -> {
////                navController.navigate(state.navigationEvent.screen.deeplink)
//            }
//
//            is NavigationEvent.None -> Unit
//        }
//    }

    NavHost(
        navController = navController,
        startDestination = Screen.TabGraph.route
    ) {
        navigation(
            startDestination = Screen.AlarmGraph().route,
            route = Screen.TabGraph.route
        ) {
            composable(route = Screen.AlarmGraph().route) {
                AlarmScreenNavGraph(modifier = modifier)
            }
            composable(route = Screen.WorldTimeGraph().route) {
                WorldTimeScreenNavGraph(modifier = modifier)
            }
            composable(route = Screen.StopwatchGraph().route) {
                StopwatchScreenNavGraph(modifier = modifier)
            }
            composable(route = Screen.TimerGraph().route) {
                TimerScreenNavHost(modifier = modifier)
            }
        }
    }
}