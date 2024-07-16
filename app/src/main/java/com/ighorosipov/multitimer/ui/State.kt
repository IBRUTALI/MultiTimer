package com.ighorosipov.multitimer.ui

import com.ighorosipov.multitimer.ui.components.navigation.NavigationEvent

data class State(
  val id: String = "",
  val navigationEvent: NavigationEvent
)