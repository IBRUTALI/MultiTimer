package com.ighorosipov.stopwatch.viewmodel

import com.ighorosipov.core_presentation.viewmodel.BaseViewModel
import com.ighorosipov.stopwatch.screen.StopwatchScreenIntent
import com.ighorosipov.stopwatch.screen.StopwatchState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StopwatchViewModel @Inject constructor(): BaseViewModel<StopwatchState, StopwatchScreenIntent>(StopwatchState()) {

}