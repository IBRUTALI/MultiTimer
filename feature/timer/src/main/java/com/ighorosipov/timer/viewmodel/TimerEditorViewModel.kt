package com.ighorosipov.timer.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.ighorosipov.core_presentation.misc.BaseUIIntent
import com.ighorosipov.core_presentation.navigation.Routes
import com.ighorosipov.core_presentation.viewmodel.BaseViewModel
import com.ighorosipov.domain.model.Ringtone
import com.ighorosipov.domain.model.Timer
import com.ighorosipov.domain.repository.RingtoneRepository
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
import java.util.UUID

@HiltViewModel(assistedFactory = TimerEditorViewModel.Factory::class)
class TimerEditorViewModel @AssistedInject constructor(
    savedStateHandle: SavedStateHandle,
    @Assisted private val screenType: TimerEditorScreenType,
    private val repository: TimerRepository,
    private val ringtoneRepository: RingtoneRepository
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
        savedStateHandle.get<String>("selected_ringtone_uri")?.let { uri ->
            val title = savedStateHandle.get<String>("selected_ringtone_title") ?: "Ringtone"
            updateState {
                state.value.copy(
                    selectedRingtone = Ringtone(title = title, uri = uri)
                )
            }
        }
    }

    fun onBackClick() {
        sendIntent(BaseUIIntent.NavigateBack)
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
                duration = timeMs
            )
        }
    }

    fun onSaveClick() {
        viewModelScope.launch {
            val timer = Timer(
                id = UUID.randomUUID().toString(),
                name = state.value.timerName,
                durationMs = state.value.duration,
                colorHex = state.value.selectedColorHex,
                ringtoneUri = state.value.selectedRingtone?.uri
            )
            repository.insertTimer(
                timer = timer
            )
        }
    }

    fun onColorChange(index: Int) {
        updateState {
            state.value.copy(
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
                        val selectedColorIndex = state.value.colors.indexOf(timer.colorHex).takeIf { it != -1 } ?: 0
                        val selectedRingtone = timer.ringtoneUri?.let { uri ->
                            Ringtone(
                                title = ringtoneRepository.findTitleByUri(uri) ?: "Unknown", //todo
                                uri = uri
                            )
                        }
                        updateState {
                            state.value.copy(
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
            val soundIsMuted = state.value.soundIsMuted
            state.value.copy(
                soundIsMuted = !soundIsMuted
            )
        }
    }

    fun onSelectRingtoneClick() {
        sendIntent(BaseUIIntent.Navigate(Routes.SelectRingtoneScreen))
    }

}