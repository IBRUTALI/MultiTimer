package com.ighorosipov.multitimer.presentation.screens.timer

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TimerViewModel @Inject constructor(

) : ViewModel() {

    private val _state = mutableStateOf(TimerState())
    val state: State<TimerState> = _state

    fun onEvent(event: TimerScreenEvent) {
        when (event) {

            TimerScreenEvent.AddTimer -> {

            }

            TimerScreenEvent.DeleteTimer -> {

            }

            TimerScreenEvent.PauseTimer -> {

            }

            TimerScreenEvent.StartTimer -> {

            }

        }
    }

}