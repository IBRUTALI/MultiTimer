package com.ighorosipov.multitimer.ui.components.navigation

import android.net.Uri
import androidx.annotation.StringRes
import androidx.core.net.toUri
import com.ighorosipov.multitimer.R

sealed class Screen(
    val route: String,
) {

    data class AlarmGraph(
        val navBarPosition: Int = 0,
    ) : Screen(route = "alarm_graph") {
        data class Alarm(
            @StringRes val labelStringId: Int = R.string.alarm,
            val deeplink: Uri = "${SCREEN_SCHEMA_DEEP_LINK}alarm_screen".toUri(),
        ) : Screen(route = "alarm_screen")
    }

    data class WorldTimeGraph(
        val navBarPosition: Int = 1,
    ) : Screen(route = "world_time_graph") {
        data class WorldTime(
            @StringRes val labelStringId: Int = R.string.world_time,
            val deeplink: Uri = "${SCREEN_SCHEMA_DEEP_LINK}world_time_screen".toUri(),
        ) : Screen(route = "world_time_screen")
    }

    data class StopwatchGraph(
        val navBarPosition: Int = 2,
    ) : Screen(route = "stopwatch_graph") {
        data class Stopwatch(
            @StringRes val labelStringId: Int = R.string.stopwatch,
            val deeplink: Uri = "${SCREEN_SCHEMA_DEEP_LINK}stopwatch_screen".toUri(),
        ) : Screen(route = "stopwatch_screen")
    }

    data class TimerGraph(
        val navBarPosition: Int = 3,
    ) : Screen(route = "timer_graph") {
        data class Timer(
            @StringRes val labelStringId: Int = R.string.timer,
        ) : Screen(route = "timer_screen")

        data class AddTimer(
            @StringRes val labelStringId: Int = R.string.add_timer,
        ) : Screen(route = "add_timer_screen")

        data class TimerDetails(
            @StringRes val labelStringId: Int = R.string.timer_details,
            val deeplink: Uri = "${SCREEN_SCHEMA_DEEP_LINK}timer_details_screen".toUri(),
        ) : Screen(route = "timer_details_screen")
    }

    companion object {
        const val SCREEN_SCHEMA_DEEP_LINK = "https://ighor-osipov.com/"
    }

}

