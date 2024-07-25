package com.ighorosipov.multitimer.feature.timer.presentation.screens.add_timer

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class AddTimerViewModel @Inject constructor(

): ViewModel() {

    private val _state = MutableStateFlow(
        AddTimerState(
            timerName = "",
            timerDuration = 0,
            color = 0
        )
    )
    val state: StateFlow<AddTimerState> = _state

    fun onEvent(event: AddTimerEvent) {
        when(event) {
            is AddTimerEvent.ChangeTimerName -> {
                _state.value = state.value.copy(
                    timerName = event.timerName
                )
            }
        }
    }

}