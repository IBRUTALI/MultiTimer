package com.ighorosipov.multitimer.ui.components.navigation

import android.net.Uri
import androidx.annotation.StringRes
import androidx.core.net.toUri
import com.ighorosipov.multitimer.R

sealed class Screen(
    val route: String,
    val deeplink: Uri,
    val navBarPosition: Int?,
) {

    data class Alarm(
        @StringRes val labelStringId: Int = R.string.alarm
    ) : Screen(
        route ="alarm_screen",
        deeplink = "${SCREEN_SCHEMA_DEEP_LINK}alarm_screen".toUri(),
        navBarPosition = 0
    )

    data class WorldTime(
        @StringRes val labelStringId: Int = R.string.world_time
    ) : Screen(
        route = "world_time_screen",
        deeplink = "${SCREEN_SCHEMA_DEEP_LINK}world_time_screen".toUri(),
        navBarPosition = 1
    )

    data class Stopwatch(
        @StringRes val labelStringId: Int = R.string.stopwatch
    ) : Screen(
        route = "stopwatch_screen",
        deeplink = "${SCREEN_SCHEMA_DEEP_LINK}stopwatch_screen".toUri(),
        navBarPosition = 2
    )

    data class Timer(
        @StringRes val labelStringId: Int = R.string.timer
    ) : Screen(
        route = "timer_screen",
        deeplink = "${SCREEN_SCHEMA_DEEP_LINK}timer_screen".toUri(),
        navBarPosition = 3
    )

    data class AddTimer(
        @StringRes val labelStringId: Int = R.string.add_timer,
    ) : Screen(
        route = "add_timer_screen",
        deeplink = "${SCREEN_SCHEMA_DEEP_LINK}add_timer_screen".toUri(),
        navBarPosition = null
    )

    data class TimerDetails(
        @StringRes val labelStringId: Int = R.string.timer_details,
    ) : Screen(
        route = "timer_details_screen",
        deeplink = "${SCREEN_SCHEMA_DEEP_LINK}timer_details_screen".toUri(),
        navBarPosition = null
    )

    companion object {
        const val SCREEN_SCHEMA_DEEP_LINK = "https://ighor-osipov.com/"
    }

}

