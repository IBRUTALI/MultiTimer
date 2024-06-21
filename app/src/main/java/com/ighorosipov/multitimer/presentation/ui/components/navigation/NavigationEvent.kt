package com.ighorosipov.multitimer.presentation.ui.components.navigation

sealed interface NavigationEvent {
    data class NavigateWithDeeplink(val screen: Screen) : NavigationEvent
    data object None : NavigationEvent
}