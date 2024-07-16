package com.ighorosipov.multitimer.feature.timer.presentation.screens.timer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ighorosipov.multitimer.di.DefaultDispatcher
import com.ighorosipov.multitimer.feature.timer.domain.use_case.GetTimersUseCase
import com.ighorosipov.multitimer.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TimerViewModel @Inject constructor(
    private val getTimersUseCase: GetTimersUseCase,
    @DefaultDispatcher
    private val dispatcher: CoroutineDispatcher,
) : ViewModel() {

    val state = MutableStateFlow(
        TimerState(
            timers = emptyList()
        )
    )

    init {
        getTimers()
    }

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

            is TimerScreenEvent.UpdateTimer -> {
//                val timers = state.value.timers.copy(
//                    time = event.time
//                )
//                state.value = state.value.copy(
//                    timers = timers
//                )
            }
        }
    }

    private fun getTimers() {
        viewModelScope.launch(dispatcher) {
            getTimersUseCase().collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        state.value = TimerState(timers = resource.data ?: emptyList())
                    }

                    is Resource.Loading -> {

                    }

                    is Resource.Error -> {

                    }
                }

            }

        }
    }

}