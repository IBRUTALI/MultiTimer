package com.ighorosipov.data.repository

import com.ighorosipov.domain.model.TimerRuntimeState
import com.ighorosipov.domain.model.TimerStatus
import com.ighorosipov.domain.model.isFinished
import com.ighorosipov.domain.model.isRunning
import com.ighorosipov.domain.repository.TimerEngine
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.sync.Mutex
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TimerEngineImpl @Inject constructor(): TimerEngine {

    private val mutex = Mutex()
    private val _states = MutableStateFlow<Map<String, TimerRuntimeState>>(emptyMap())
    override val states: StateFlow<Map<String, TimerRuntimeState>> = _states.asStateFlow()

    override fun snapshot(): List<TimerRuntimeState> = _states.value.values.toList()

    override fun hasRunning(): Boolean = _states.value.values.any { it.status.isRunning() }

    override fun start(id: String, durationMs: Long, name: String, ringtoneUri: String?) {
        val current = _states.value[id]
        val remaining = when {
            current == null || current.status.isFinished() -> durationMs
            else -> current.remainingMs
        }
        _states.update {
            it + (id to TimerRuntimeState(
                id = id,
                name = name,
                remainingMs = remaining,
                durationMs = durationMs,
                ringtoneUri = ringtoneUri,
                status = TimerStatus.RUNNING
            ))
        }
    }

    override fun pause(id: String) {
        _states.update { map ->
            map[id]?.let { map + (id to it.copy(status = TimerStatus.PAUSED)) } ?: map
        }
    }

    override fun stop(id: String, durationMs: Long) {
        _states.update { map ->
            map[id]?.let {
                map + (id to it.copy(
                    remainingMs = durationMs,
                    status = TimerStatus.IDLE
                ))
            } ?: map
        }
    }

    override fun stopAll() {
        _states.value = emptyMap()
    }

    override fun tick(deltaMs: Long): List<TimerRuntimeState> {
        val finished = mutableListOf<TimerRuntimeState>()
        _states.update { map ->
            map.mapValues { (_, state) ->
                if (!state.status.isRunning() || state.status.isFinished()) state
                else {
                    val left = (state.remainingMs - deltaMs).coerceAtLeast(0L)
                    val newState = state.copy(
                        remainingMs = left,
                        status = when {
                            left > 0 -> TimerStatus.RUNNING
                            else -> TimerStatus.FINISHED
                        }
                    )
                    if (newState.status.isFinished()) finished += newState
                    newState
                }
            }
        }
        return finished
    }
}