package com.ighorosipov.stopwatch.viewmodel

import androidx.lifecycle.viewModelScope
import com.ighorosipov.core_presentation.viewmodel.BaseViewModel
import com.ighorosipov.domain.stopwatch.StopwatchStateObserver
import com.ighorosipov.domain.stopwatch.model.StopwatchStatus
import com.ighorosipov.domain.stopwatch.use_case.LapStopwatchUseCase
import com.ighorosipov.domain.stopwatch.use_case.PauseStopwatchUseCase
import com.ighorosipov.domain.stopwatch.use_case.ResumeStopwatchUseCase
import com.ighorosipov.domain.stopwatch.use_case.StartStopwatchUseCase
import com.ighorosipov.domain.stopwatch.use_case.StopStopwatchUseCase
import com.ighorosipov.stopwatch.screen.LapUiModel
import com.ighorosipov.stopwatch.screen.StopwatchScreenIntent
import com.ighorosipov.stopwatch.screen.StopwatchState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val SINGLE_STOPWATCH_ID = "single_stopwatch"

@HiltViewModel
class StopwatchViewModel @Inject constructor(
    private val startStopwatchUseCase: StartStopwatchUseCase,
    private val pauseStopwatchUseCase: PauseStopwatchUseCase,
    private val resumeStopwatchUseCase: ResumeStopwatchUseCase,
    private val lapStopwatchUseCase: LapStopwatchUseCase,
    private val stopStopwatchUseCase: StopStopwatchUseCase,
    private val stateObserver: StopwatchStateObserver
) : BaseViewModel<StopwatchState, StopwatchScreenIntent>(StopwatchState()) {

    init {
        observeEngineState()
    }

    private fun observeEngineState() {
        viewModelScope.launch {
            stateObserver.states().collect { statesMap ->
                val stopwatchState = statesMap[SINGLE_STOPWATCH_ID]
                updateState {
                    copy(
                        elapsedMs = stopwatchState?.elapsedMs ?: 0L,
                        status = stopwatchState?.status ?: StopwatchStatus.IDLE,
                        laps = stopwatchState?.laps?.map { lap ->
                            LapUiModel(
                                index = lap.index,
                                splitTimeMs = lap.splitTimeMs,
                                totalTimeMs = lap.totalTimeMs
                            )
                        } ?: emptyList(),
                        progress = if (stopwatchState != null && stopwatchState.elapsedMs > 0) {
                            ((stopwatchState.elapsedMs % 60_000L) / 60_000f)
                        } else 0f
                    )
                }
            }
        }
    }

    fun onStart() {
        startStopwatchUseCase(SINGLE_STOPWATCH_ID, name = "Stopwatch", colorHex = "#6750A4")
    }

    fun onPause() {
        pauseStopwatchUseCase(SINGLE_STOPWATCH_ID)
    }

    fun onResume() {
        resumeStopwatchUseCase(SINGLE_STOPWATCH_ID)
    }

    fun onLap() {
        lapStopwatchUseCase(SINGLE_STOPWATCH_ID)
    }

    fun onReset() {
        stopStopwatchUseCase(SINGLE_STOPWATCH_ID)
    }
}