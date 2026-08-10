package com.ighorosipov.multitimer

import com.ighorosipov.core_presentation.viewmodel.BaseViewModel
import com.ighorosipov.multitimer.ui.MainActivityIntent
import com.ighorosipov.multitimer.ui.MainState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
) : BaseViewModel<MainState, MainActivityIntent>(
    initialState = MainState()
) {



}