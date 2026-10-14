package com.ighorosipov.timer.viewmodel

import androidx.lifecycle.viewModelScope
import com.ighorosipov.core_presentation.misc.BaseUIIntent
import com.ighorosipov.core_presentation.navigation.Routes
import com.ighorosipov.core_presentation.viewmodel.BaseViewModel
import com.ighorosipov.domain.model.Timer
import com.ighorosipov.domain.repository.TimerEngine
import com.ighorosipov.domain.repository.TimerRepository
import com.ighorosipov.timer.model.toUiModel
import com.ighorosipov.timer.screen.timer.TimerScreenIntent
import com.ighorosipov.timer.screen.timer.TimerState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TimerViewModel @Inject constructor(
    private val repository: TimerRepository,
    private val engine: TimerEngine
) : BaseViewModel<TimerState, TimerScreenIntent>(TimerState()) {

    init {
        getTimers()
    }

    private fun getTimers() {
        viewModelScope.launch {
            combine(
                repository.observeTimers(),
                engine.states
            ) { timers, runtime ->
                timers.map { timer ->
                    timer.toUiModel(runtime[timer.id])
                }
            }.collect { uiTimers ->
                updateState {
                    state.value.copy(timers = uiTimers)
                }
            }
        }
    }

    fun onAddTimerClick() {
        sendIntent(
            intent = BaseUIIntent.Navigate(
                Routes.TimerEditorScreen.Add
            )
        )
    }

    fun onEditTimerClick(
        timer: Timer
    ) {
        sendIntent(
            intent = BaseUIIntent.Navigate(
                Routes.TimerEditorScreen.Edit(
                    timerId = timer.id
                )
            )
        )
    }

}