package com.ighorosipov.utils

import kotlinx.serialization.Serializable

@Serializable
sealed class TimerEditorScreenType {
    @Serializable
    data object AddType: TimerEditorScreenType()
    @Serializable
    data class EditType(val timerId: String): TimerEditorScreenType()
}