package com.ighorosipov.timer.viewmodel

import com.ighorosipov.core_presentation.misc.BaseUIIntent
import com.ighorosipov.core_presentation.viewmodel.BaseViewModel
import com.ighorosipov.timer.screen.add_timer.TimerEditorScreenIntent
import com.ighorosipov.timer.screen.add_timer.TimerEditorState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TimerEditorViewModel @Inject constructor(
) : BaseViewModel<TimerEditorState, TimerEditorScreenIntent>(TimerEditorState()) {

    fun onBackClick() {
        sendIntent(BaseUIIntent.NavigateBack)
    }

}