package com.ighorosipov.ringtone.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.ighorosipov.core_presentation.misc.BaseUIIntent
import com.ighorosipov.core_presentation.misc.RingtoneResult
import com.ighorosipov.core_presentation.misc.RingtoneResultBus
import com.ighorosipov.core_presentation.viewmodel.BaseViewModel
import com.ighorosipov.domain.ringtone.model.Ringtone
import com.ighorosipov.domain.player.repository.PlayerRepository
import com.ighorosipov.domain.ringtone.repository.RingtoneRepository
import com.ighorosipov.ringtone.screen.SelectRingtoneScreenIntent
import com.ighorosipov.ringtone.screen.SelectRingtoneState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelectRingtoneViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val ringtoneRepository: RingtoneRepository,
    private val playerRepository: PlayerRepository,
    private val resultBus: RingtoneResultBus
) : BaseViewModel<SelectRingtoneState, SelectRingtoneScreenIntent>(SelectRingtoneState()) {

    init {
        loadRingtones()
    }

    private fun loadRingtones() {
        viewModelScope.launch {
            updateState {
                copy(
                    isLoading = true
                )
            }
            val ringtones = ringtoneRepository.getRingtones()
            updateState {
                copy(
                    isLoading = false,
                    ringtones = ringtones
                )
            }
        }
    }

    fun onRingtoneClick(ringtone: Ringtone) {
        updateState {
            copy(selectedRingtone = ringtone)
        }
    }

    fun onPlayPauseClick(ringtone: Ringtone) {
        viewModelScope.launch {
            if (state.value.playingRingtoneUri == ringtone.uri) {
                playerRepository.stop()
                updateState {
                    copy(playingRingtoneUri = null)
                }
            } else {
                if (playerRepository.isPlaying()) {
                    playerRepository.stop()
                }
                playerRepository.play(ringtone.uri)
                updateState { copy(playingRingtoneUri = ringtone.uri) }
            }
        }
    }

    fun onAddCustomRingtone(title: String, uri: String) {
        val customRingtone = Ringtone(title = title, uri = uri)
        updateState {
            val updatedRingtones = listOf(customRingtone) + ringtones
            copy(
                ringtones = updatedRingtones,
                selectedRingtone = customRingtone
            )
        }
    }

    fun onSaveClick() {
        stopPlayer()
        state.value.selectedRingtone?.let { ringtone ->
            resultBus.emit(
                RingtoneResult(
                    uri = ringtone.uri,
                    title = ringtone.title
                )
            )
        }
        sendIntent(BaseUIIntent.NavigateBack)
    }

    fun onBackClick() {
        stopPlayer()
        sendIntent(BaseUIIntent.NavigateBack)
    }

    private fun stopPlayer() {
        if (playerRepository.isPlaying()) {
            viewModelScope.launch {
                playerRepository.stop()
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        stopPlayer()
    }
}
