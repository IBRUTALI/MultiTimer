package com.ighorosipov.multitimer.presentation.ui.components.navigation

import android.net.Uri
import androidx.core.net.toUri

sealed class Screen(val route: String, val deeplink: Uri, val navBarPosition: Int?) {

    data object AlarmScreen : Screen(
        "alarm_screen",
        "${SCREEN_SCHEMA_DEEP_LINK}alarm_screen".toUri(),
        0
    )

    data object WorldTimeScreen : Screen(
        "world_time_screen",
        "${SCREEN_SCHEMA_DEEP_LINK}world_time_screen".toUri(),
        1
    )

    data object StopwatchScreen : Screen(
        "stopwatch_screen",
        "${SCREEN_SCHEMA_DEEP_LINK}stopwatch_screen".toUri(),
        2
    )

    data object TimerScreen : Screen(
        "timer_screen",
        "${SCREEN_SCHEMA_DEEP_LINK}timer_screen".toUri(),
        3
    )

    companion object {
        const val SCREEN_SCHEMA_DEEP_LINK= "https://ighor-osipov.com/"
    }

}

