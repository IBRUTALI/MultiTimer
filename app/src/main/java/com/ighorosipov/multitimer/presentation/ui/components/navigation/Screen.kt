package com.ighorosipov.multitimer.presentation.ui.components.navigation

sealed class Screen(val route: String) {

    data object AlarmScreen: Screen("alarm_screen")

    data object WorldTimeScreen: Screen("world_time_screen")

    data object StopwatchScreen: Screen("stopwatch_screen")

    data object TimerScreen: Screen("timer_screen")

    companion object {
        val ROOT_DEEP_LINK = "https://ighor-osipov.com"
        //val TIMER_SCREEN_DEEP_LINK = "${Graph.ROOT}://${TimerScreen.route}"
        const val TIMER_SCREEN_DEEP_LINK = "https://ighor-osipov.com/timer_screen"
    }

}

