package com.ighorosipov.core_presentation.navigation

import android.os.Bundle
import androidx.annotation.DrawableRes
import com.ighorosipov.utils.R
import kotlinx.serialization.Serializable

sealed class BottomNavItems(
    val screenRoute: Any,
    @DrawableRes val icon: Int
) {
    data object AlarmScreens :
        BottomNavItems(Routes.AlarmGraph, R.drawable.ic_alarm_fill)

    data object WorldTimeScreens :
        BottomNavItems(Routes.WorldTimeGraph, R.drawable.ic_earth_fill)

    data object StopwatchScreens :
        BottomNavItems(Routes.StopwatchGraph, R.drawable.ic_stopwatch_fill)

    data object TimerScreens :
        BottomNavItems(Routes.TimerGraph, R.drawable.ic_timer_fill)

}

@Serializable
sealed class Routes {

    companion object {
        fun fromRoute(route: String, args: Bundle?): Routes? {
            val subclass = Routes::class.sealedSubclasses.firstOrNull {
                route.contains(it.qualifiedName.toString())
            }
            return subclass?.let { createInstance(it, args) }
        }
    }

    @Serializable
    data object AlarmGraph : Routes()

    @Serializable
    data object WorldTimeGraph : Routes()

    @Serializable
    data object StopwatchGraph : Routes()

    @Serializable
    data object TimerGraph : Routes()

    @Serializable
    data object AlarmScreen : Routes()

    @Serializable
    data object WorldTimeScreen : Routes()

    @Serializable
    data object StopwatchScreen : Routes()

    @Serializable
    data object TimerScreen : Routes()
    @Serializable
    data object AddTimerScreen : Routes()
    @Serializable
    data object TimerDetailsScreen : Routes()

}