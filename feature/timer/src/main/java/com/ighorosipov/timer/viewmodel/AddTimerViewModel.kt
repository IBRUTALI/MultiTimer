package com.ighorosipov.timer.viewmodel

import com.ighorosipov.core_presentation.viewmodel.BaseViewModel
import com.ighorosipov.timer.screen.add_timer.AddTimerScreenIntent
import com.ighorosipov.timer.screen.add_timer.AddTimerState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddTimerViewModel @Inject constructor(
) : BaseViewModel<AddTimerState, AddTimerScreenIntent>(AddTimerState()) {

}