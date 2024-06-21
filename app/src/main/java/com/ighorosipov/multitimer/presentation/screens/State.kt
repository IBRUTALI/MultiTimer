package com.ighorosipov.multitimer.presentation.screens

import com.ighorosipov.multitimer.presentation.ui.components.navigation.NavigationEvent

data class State(
  val id: String = "",
  val navigationEvent: NavigationEvent
)