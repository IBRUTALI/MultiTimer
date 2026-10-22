package com.ighorosipov.timer.viewmodel

import androidx.lifecycle.viewModelScope
import com.ighorosipov.core_presentation.misc.BaseUIIntent
import com.ighorosipov.core_presentation.navigation.Routes
import com.ighorosipov.core_presentation.viewmodel.BaseViewModel
import com.ighorosipov.domain.timer.TimerStateObserver
import com.ighorosipov.domain.timer.repository.TimerEngine
import com.ighorosipov.domain.timer.repository.TimerRepository
import com.ighorosipov.domain.timer.use_case.PauseTimerUseCase
import com.ighorosipov.domain.timer.use_case.StartTimerUseCase
import com.ighorosipov.domain.timer.use_case.StopTimerUseCase
import com.ighorosipov.timer.model.toUiModel
import com.ighorosipov.timer.screen.timer.TimerScreenIntent
import com.ighorosipov.timer.screen.timer.TimerState
import com.ighorosipov.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TimerViewModel @Inject constructor(
    private val startTimerUseCase: StartTimerUseCase,
    private val pauseTimerUseCase: PauseTimerUseCase,
    private val stopTimerUseCase: StopTimerUseCase,
    private val timerStateObserver: TimerStateObserver,
    private val timerRepository: TimerRepository,
    private val engine: TimerEngine
) : BaseViewModel<TimerState, TimerScreenIntent>(TimerState()) {

    init {
        getTimers()
    }

    private fun getTimers() {
        viewModelScope.launch {
            combine(
                timerRepository.observeTimers(),
                timerStateObserver.states()
            ) { timers, runtime ->
                timers.map { timer ->
                    timer.toUiModel(runtime[timer.id])
                }
            }.collect { uiTimers ->
                updateState {
                    copy(timers = uiTimers)
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
        timerId: String
    ) {
        sendIntent(
            intent = BaseUIIntent.Navigate(
                Routes.TimerEditorScreen.Edit(
                    timerId = timerId
                )
            )
        )
    }

    fun onDeleteTimer(
        timerId: String
    ): Boolean {
        var deleteResult = false
        viewModelScope.launch {
            timerRepository.deleteTimer(
                timerId = timerId
            ).also { result ->
                deleteResult = when(result) {
                    is Resource.Error<*> -> {
                        false
                    }

                    is Resource.Success<*> -> {
                        true
                    }
                }
            }
        }
        return deleteResult
    }

    fun onPlayPauseClick(timerId: String) {
        viewModelScope.launch {
            val timer = state.value.timers.find { it.id == timerId } ?: return@launch // todo null handle
            val isRunning = timerStateObserver.isRunning(timer.id)
            if (isRunning) {
                pauseTimerUseCase(timer.id)
            } else {
                startTimerUseCase(
                    id = timer.id,
                    durationMs = timer.durationMs,
                    name = timer.name,
                    ringtoneUri = timer.ringtoneUri
                )
            }
        }
    }

    fun onStopClick(timerId: String) {

    }

}