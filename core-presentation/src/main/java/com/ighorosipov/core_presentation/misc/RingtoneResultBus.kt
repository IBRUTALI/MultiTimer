package com.ighorosipov.core_presentation.misc

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RingtoneResultBus @Inject constructor() {
    private val _events = MutableSharedFlow<RingtoneResult>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val events = _events.asSharedFlow()
    fun emit(result: RingtoneResult) { _events.tryEmit(result) }
}

data class RingtoneResult(val uri: String, val title: String)