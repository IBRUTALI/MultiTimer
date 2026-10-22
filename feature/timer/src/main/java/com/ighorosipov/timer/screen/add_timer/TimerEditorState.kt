package com.ighorosipov.timer.screen.add_timer

import com.ighorosipov.core_presentation.misc.TimerColorPalette
import com.ighorosipov.domain.ringtone.model.Ringtone
import com.ighorosipov.utils.TimerEditorScreenType

data class TimerEditorState(
    val isLoading: Boolean = false,
    val screenType: TimerEditorScreenType = TimerEditorScreenType.Add,
    val timerName: String = "",
    val initialDuration: Long = 0,
    val duration: Long = 0,
    val selectedColorIndex: Int = 0,
    val colors: List<String> = TimerColorPalette.colors,
    val ringtones: List<Ringtone> = emptyList(),
    val selectedRingtoneIndex: Int? = null,
    val selectedRingtone: Ringtone? = null,
    val soundIsMuted: Boolean = false,
    val isSaving: Boolean = false
) {
    val selectedColorHex: String
        get() = colors[selectedColorIndex]
}

sealed class TimerEditorScreenIntent {
    // todo
}
