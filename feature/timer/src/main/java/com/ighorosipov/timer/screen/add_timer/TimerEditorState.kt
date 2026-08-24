package com.ighorosipov.timer.screen.add_timer

import com.ighorosipov.domain.model.Ringtone
import com.ighorosipov.utils.TimerEditorScreenType

data class TimerEditorState(
    val screenType: TimerEditorScreenType = TimerEditorScreenType.Add,
    val timerName: String = "",
    val initialTime: Long = 0,
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

sealed class TimerEditorScreenIntent {
    // todo
}
