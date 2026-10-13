package com.ighorosipov.ringtone.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.ighorosipov.core_presentation.misc.BaseUIIntent
import com.ighorosipov.core_presentation.viewmodel.BaseViewModel
import com.ighorosipov.domain.model.Ringtone
import com.ighorosipov.domain.repository.PlayerRepository
import com.ighorosipov.domain.repository.RingtoneRepository
import com.ighorosipov.ringtone.screen.SelectRingtoneScreenIntent
import com.ighorosipov.ringtone.screen.SelectRingtoneState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelectRingtoneViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val ringtoneRepository: RingtoneRepository,
    private val playerRepository: PlayerRepository
) : BaseViewModel<SelectRingtoneState, SelectRingtoneScreenIntent>(SelectRingtoneState()) {

    init {
        loadRingtones()
    }

    private fun loadRingtones() {
        viewModelScope.launch {
            val ringtones = ringtoneRepository.getRingtones()
            updateState {
                state.value.copy(ringtones = ringtones)
            }
        }
    }

    fun onRingtoneClick(ringtone: Ringtone) {
        updateState {
            state.value.copy(selectedRingtone = ringtone)
        }
    }

    fun onPlayPauseClick(ringtone: Ringtone) {
        viewModelScope.launch {
            if (state.value.playingRingtoneUri == ringtone.uri) {
                playerRepository.stop()
                updateState { state.value.copy(playingRingtoneUri = null) }
            } else {
                if (playerRepository.isPlaying()) {
                    playerRepository.stop()
                }
                playerRepository.play(ringtone.uri)
                updateState { state.value.copy(playingRingtoneUri = ringtone.uri) }
            }
        }
    }

    fun onAddCustomRingtone(title: String, uri: String) {
        val customRingtone = Ringtone(title = title, uri = uri)
        updateState {
            val updatedRingtones = listOf(customRingtone) + state.value.ringtones
            state.value.copy(
                ringtones = updatedRingtones,
                selectedRingtone = customRingtone
            )
        }
    }

    fun onSaveClick() {
        stopPlayer()
        state.value.selectedRingtone?.let { ringtone ->
            savedStateHandle["selected_ringtone_uri"] = ringtone.uri
            savedStateHandle["selected_ringtone_title"] = ringtone.title
        }
        sendIntent(BaseUIIntent.NavigateBack)
    }

    fun onBackClick() {
        stopPlayer()
        sendIntent(BaseUIIntent.NavigateBack)
    }

    private fun stopPlayer() {
        try {
            if (playerRepository.isPlaying()) {
                viewModelScope.launch {
                    playerRepository.stop()
                }
            }
        } catch (_: Exception) {}
    }

    override fun onCleared() {
        super.onCleared()
        stopPlayer()
    }
}
