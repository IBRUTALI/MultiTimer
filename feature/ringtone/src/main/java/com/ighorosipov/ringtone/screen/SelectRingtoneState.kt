package com.ighorosipov.ringtone.screen

import com.ighorosipov.domain.model.Ringtone

data class SelectRingtoneState(
    val ringtones: List<Ringtone> = emptyList(),
    val selectedRingtone: Ringtone? = null,
    val playingRingtoneUri: String? = null
)

sealed class SelectRingtoneScreenIntent {
    // todo
}
