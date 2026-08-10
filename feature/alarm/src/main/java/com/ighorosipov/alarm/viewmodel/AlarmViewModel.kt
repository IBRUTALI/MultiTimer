package com.ighorosipov.alarm.viewmodel

import com.ighorosipov.alarm.screen.AlarmScreenIntent
import com.ighorosipov.alarm.screen.AlarmState
import com.ighorosipov.core_presentation.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AlarmViewModel @Inject constructor(): BaseViewModel<AlarmState, AlarmScreenIntent>(AlarmState()) {

}