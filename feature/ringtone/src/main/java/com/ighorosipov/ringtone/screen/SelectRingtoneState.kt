package com.ighorosipov.ringtone.screen

import com.ighorosipov.domain.ringtone.model.Ringtone

data class SelectRingtoneState(
    val isLoading: Boolean = false,
    val ringtones: List<Ringtone> = emptyList(),
    val selectedRingtone: Ringtone? = null,
    val playingRingtoneUri: String? = null
)

sealed class SelectRingtoneScreenIntent {
    // todo
}
