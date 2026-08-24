package com.ighorosipov.timer.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.ighorosipov.core_presentation.misc.BaseUIIntent
import com.ighorosipov.core_presentation.navigation.Routes
import com.ighorosipov.core_presentation.viewmodel.BaseViewModel
import com.ighorosipov.domain.repository.TimerRepository
import com.ighorosipov.timer.screen.add_timer.TimerEditorScreenIntent
import com.ighorosipov.timer.screen.add_timer.TimerEditorState
import com.ighorosipov.utils.Resource
import com.ighorosipov.utils.TimerEditorScreenType
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = TimerEditorViewModel.Factory::class)
class TimerEditorViewModel @AssistedInject constructor(
    savedStateHandle: SavedStateHandle,
    @Assisted private val screenType: TimerEditorScreenType,
    private val repository: TimerRepository
) : BaseViewModel<TimerEditorState, TimerEditorScreenIntent>(
    initialState = TimerEditorState()
) {

    @AssistedFactory
    interface Factory {
        fun create(screenType: TimerEditorScreenType): TimerEditorViewModel
    }

    init {
        when (screenType) {
            is TimerEditorScreenType.Add -> {

            }

            is TimerEditorScreenType.Edit -> {
                val args = savedStateHandle.toRoute<Routes.TimerEditorScreen.Edit>()
                loadTimer(
                    timerId = args.timerId
                )
            }
        }
    }

    fun onBackClick() {
        sendIntent(BaseUIIntent.NavigateBack)
    }

    fun onInitialTimeClick(timeMs: Long) {

    }

    fun onTimerNameChange(value: String) {
        updateState {
            state.value.copy(
                timerName = value
            )
        }
    }

    fun onTimeChange(timeMs: Long) {
        updateState {
            state.value.copy(
                time = timeMs
            )
        }
    }

    private fun loadTimer(timerId: String) {
        viewModelScope.launch {
            repository.getTimerById(
                timerId = timerId
            ).also { result ->
                when (result) {
                    is Resource.Error<*> -> {

                    }

                    is Resource.Loading<*> -> {

                    }

                    is Resource.Success<*> -> {
                        val timer = result.data ?: return@launch
                        updateState {
                            state.value.copy(
                                timerName = timer.name,
                                time = timer.time,
                                initialTime = timer.time,
                                color = timer.color,
                            )
                        }
                    }
                }
            }
        }
    }

}