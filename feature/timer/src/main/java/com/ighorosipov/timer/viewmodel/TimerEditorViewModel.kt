package com.ighorosipov.timer.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.ighorosipov.core_presentation.misc.BaseUIIntent
import com.ighorosipov.core_presentation.misc.RingtoneResultBus
import com.ighorosipov.core_presentation.navigation.Routes
import com.ighorosipov.core_presentation.viewmodel.BaseViewModel
import com.ighorosipov.domain.ringtone.model.Ringtone
import com.ighorosipov.domain.timer.model.Timer
import com.ighorosipov.domain.ringtone.repository.RingtoneRepository
import com.ighorosipov.domain.timer.repository.TimerRepository
import com.ighorosipov.timer.screen.add_timer.TimerEditorScreenIntent
import com.ighorosipov.timer.screen.add_timer.TimerEditorState
import com.ighorosipov.utils.Resource
import com.ighorosipov.utils.TimerEditorScreenType
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.UUID

@HiltViewModel(assistedFactory = TimerEditorViewModel.Factory::class)
class TimerEditorViewModel @AssistedInject constructor(
    savedStateHandle: SavedStateHandle,
    @Assisted private val screenType: TimerEditorScreenType,
    private val repository: TimerRepository,
    private val ringtoneRepository: RingtoneRepository,
    private val resultBus: RingtoneResultBus
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
        resultBus.events
            .onEach { result ->
                updateState {
                    copy(
                        selectedRingtone = Ringtone(
                            title = result.title,
                            uri = result.uri
                        )
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    fun onBackClick() {
        sendIntent(BaseUIIntent.NavigateBack)
    }

    fun onTimerNameChange(value: String) {
        updateState {
            copy(
                timerName = value
            )
        }
    }

    fun onTimeChange(timeMs: Long) {
        updateState {
            copy(
                duration = timeMs
            )
        }
    }

    fun onSaveClick() {
        if (state.value.isSaving) return
        viewModelScope.launch {
            updateState {
                copy(
                    isSaving = true,
                    isLoading = true
                )
            }
            val timer = Timer(
                id = UUID.randomUUID().toString(),
                name = state.value.timerName,
                durationMs = state.value.duration,
                colorHex = state.value.selectedColorHex,
                ringtoneUri = state.value.selectedRingtone?.uri
            )
            repository.insertTimer(
                timer = timer
            ).also { result ->
                when (result) {
                    is Resource.Error<*> -> {
                        updateState {
                            copy(
                                isSaving = false,
                                isLoading = false
                            )
                        }
                    }

                    is Resource.Success<*> -> {
                        updateState {
                            copy(isLoading = false)
                        }
                        sendIntent(BaseUIIntent.NavigateBack)
                    }
                }
            }
        }
    }

    fun onColorChange(index: Int) {
        updateState {
            copy(
                selectedColorIndex = index
            )
        }
    }

    private fun getRingtones() {

    }

    private fun loadTimer(timerId: String) {
        viewModelScope.launch {
            repository.getTimerById(
                timerId = timerId
            ).also { result ->
                when (result) {
                    is Resource.Error<*> -> {
                        //todo
                    }

                    is Resource.Success<*> -> {
                        val timer = result.data ?: return@launch
                        val selectedColorIndex =
                            state.value.colors.indexOf(timer.colorHex).takeIf { it != -1 } ?: 0
                        val selectedRingtone = timer.ringtoneUri?.let { uri ->
                            Ringtone(
                                title = ringtoneRepository.findTitleByUri(uri)
                                    ?: "Unknown", //todo
                                uri = uri
                            )
                        }
                        updateState {
                            copy(
                                timerName = timer.name,
                                duration = timer.durationMs,
                                initialDuration = timer.durationMs,
                                selectedColorIndex = selectedColorIndex,
                                selectedRingtone = selectedRingtone
                            )
                        }
                    }
                }
            }
        }
    }

    fun onSwitchSoundClick() {
        updateState {
            copy(
                soundIsMuted = !soundIsMuted
            )
        }
    }

    fun onSelectRingtoneClick() {
        sendIntent(BaseUIIntent.Navigate(Routes.SelectRingtoneScreen))
    }

}