package com.ighorosipov.multitimer.feature.timer.presentation.screens.add_timer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ighorosipov.multitimer.feature.player.domain.use_case.PlayUseCase
import com.ighorosipov.multitimer.feature.player.domain.use_case.StopUseCase
import com.ighorosipov.multitimer.feature.ringtone.domain.use_case.GetRingtonesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddTimerViewModel @Inject constructor(
    private val playUseCase: PlayUseCase,
    private val stopUseCase: StopUseCase,
    private val getRingtonesUseCase: GetRingtonesUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(
        AddTimerState(color = 0)
    )
    val state: StateFlow<AddTimerState> = _state

    init {
        getRingtones()
    }

    fun onEvent(event: AddTimerEvent) {
        when (event) {
            is AddTimerEvent.ChangeTimerName -> {
                _state.value = state.value.copy(
                    timerName = event.timerName
                )
            }

            is AddTimerEvent.ChangeCustomDurationCheck -> {
                _state.value = state.value.copy(
                    customDurationEnabled = event.isChecked
                )
            }

            is AddTimerEvent.ChangeCustomTimerText -> {
                _state.value = state.value.copy(
                    customDurationText = event.text
                )
            }

            is AddTimerEvent.ChangeTimerDuration -> {
                _state.value = state.value.copy(
                    time = event.value
                )
            }

            is AddTimerEvent.ChangeTimerColor -> {
                _state.value = state.value.copy(
                    selectedColorIndex = event.index
                )
            }

            is AddTimerEvent.RingtoneCheck -> {
                viewModelScope.launch(Dispatchers.Default) {
                    if (state.value.selectedRingtoneUri == event.uri &&
                        state.value.playingRingtone.first.uri == event.uri &&
                        state.value.playingRingtone.second
                    ) {
                        stopUseCase()
                        _state.value = state.value.copy(
                            playingRingtone = state.value.ringtones[event.index] to false
                        )
                    } else if (state.value.selectedRingtoneUri == event.uri &&
                        state.value.playingRingtone.first.uri == event.uri &&
                        !state.value.playingRingtone.second
                    ) {
                        playUseCase(event.uri)
                        _state.value = state.value.copy(
                            playingRingtone = state.value.ringtones[event.index] to true
                        )
                    }
                    if (state.value.selectedRingtoneUri != event.uri) {
                        playUseCase(event.uri)
                        _state.value = state.value.copy(
                            playingRingtone = state.value.ringtones[event.index] to true
                        )
                    }

                    _state.value = state.value.copy(
                        selectedRingtoneIndex = event.index,
                        selectedRingtoneUri = event.uri
                    )
                }
            }
        }
    }

    private fun getRingtones() {
        viewModelScope.launch(Dispatchers.IO) {
            getRingtonesUseCase().collect { ringtones ->
                _state.value = state.value.copy(
                    ringtones = ringtones
                )
            }
        }
    }

}