package com.ighorosipov.multitimer.presentation.screens.timer

import androidx.lifecycle.ViewModel
import com.ighorosipov.multitimer.domain.use_case.AddTimerUseCase
import com.ighorosipov.multitimer.domain.use_case.DeleteTimerUseCase
import com.ighorosipov.multitimer.domain.use_case.StartTimerUseCase
import com.ighorosipov.multitimer.domain.use_case.PauseTimerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TimerViewModel @Inject constructor(
    private val addTimerUseCase: AddTimerUseCase,
    private val deleteTimerUseCase: DeleteTimerUseCase,
    private val startTimerUseCase: StartTimerUseCase,
    private val pauseTimerUseCase: PauseTimerUseCase
): ViewModel() {

    fun onEvent(event: TimerScreenEvent) {

    }

}