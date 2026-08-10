package com.ighorosipov.timer.screen.add_timer

import com.ighorosipov.domain.model.Ringtone

data class AddTimerState(
    val timerName: String = "",
    val customDurationEnabled: Boolean = false,
    val customDurationText: String = "",
    val time: Long = 0,
    val selectedColorIndex: Int = 0,
    val color: Int = 0,
    val tone: String? = null,
    val ringtones: List<Ringtone> = emptyList(),
    val selectedRingtoneIndex: Int = 0,
    val selectedRingtoneUri: String = "",
    val playingRingtone: Pair<Ringtone, Boolean> = Ringtone(
        title = "",
        uri = ""
    ) to false
)

sealed class AddTimerScreenIntent {
    // todo
}
