package com.ighorosipov.multitimer.presentation.ui.components.navigation

sealed class Screen(val route: String) {

    data object AlarmScreen: Screen("alarm_screen")

    data object WorldTimeScreen: Screen("world_time_screen")

    data object StopwatchScreen: Screen("stopwatch_screen")

    data object TimerScreen: Screen("timer_screen")

}