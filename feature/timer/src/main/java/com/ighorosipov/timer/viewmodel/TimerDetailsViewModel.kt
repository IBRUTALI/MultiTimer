package com.ighorosipov.timer.viewmodel

import com.ighorosipov.core_presentation.viewmodel.BaseViewModel
import com.ighorosipov.timer.screen.timer_details.TimerDetailsScreenIntent
import com.ighorosipov.timer.screen.timer_details.TimerDetailsState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TimerDetailsViewModel @Inject constructor(
) : BaseViewModel<TimerDetailsState, TimerDetailsScreenIntent>(TimerDetailsState()) {

}