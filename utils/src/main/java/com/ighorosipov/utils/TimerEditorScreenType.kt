package com.ighorosipov.utils

sealed class TimerEditorScreenType {

    data object Add: TimerEditorScreenType()

    data object Edit: TimerEditorScreenType()
}