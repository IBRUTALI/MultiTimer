package com.ighorosipov.timer.viewmodel

import androidx.lifecycle.viewModelScope
import com.ighorosipov.core_presentation.misc.BaseUIIntent
import com.ighorosipov.core_presentation.navigation.Routes
import com.ighorosipov.core_presentation.viewmodel.BaseViewModel
import com.ighorosipov.domain.model.Timer
import com.ighorosipov.domain.use_case.GetTimersUseCase
import com.ighorosipov.timer.screen.timer.TimerScreenIntent
import com.ighorosipov.timer.screen.timer.TimerState
import com.ighorosipov.utils.Resource
import com.ighorosipov.utils.TimerEditorScreenType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TimerViewModel @Inject constructor(
    private val getTimersUseCase: GetTimersUseCase
) : BaseViewModel<TimerState, TimerScreenIntent>(TimerState()) {

    init {
        getTimers()
    }

    private fun getTimers() {
        viewModelScope.launch {
            getTimersUseCase().collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        updateState {
                            state.value.copy(
                                timers = resource.data ?: emptyList()
                            )
                        }
                    }

                    is Resource.Loading -> {
                        // todo
                    }

                    is Resource.Error -> {
                        // todo
                    }
                }

            }

        }
    }

    fun onAddTimerClick() {
        sendIntent(
            intent = BaseUIIntent.Navigate(Routes.TimerEditorScreen(
                screenType = TimerEditorScreenType.AddType)
            )
        )
    }

    fun onEditTimerClick(
        timer: Timer
    ) {
        sendIntent(
            intent = BaseUIIntent.Navigate(Routes.TimerEditorScreen(
                screenType = TimerEditorScreenType.EditType(timerId = timer.id))
            )
        )
    }

}