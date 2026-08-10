package com.ighorosipov.multitimer.ui

import com.ighorosipov.core_presentation.navigation.Routes

data class MainState(
    val splashCondition: Boolean = true,
    val startDestination: Routes = Routes.AlarmGraph,
)

sealed class MainActivityIntent {
    // todo
}

