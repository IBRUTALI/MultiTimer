package com.ighorosipov.data.timer.repository

import com.ighorosipov.domain.timer.model.TimerRuntimeState
import com.ighorosipov.domain.timer.model.TimerStatus
import com.ighorosipov.domain.timer.model.isFinished
import com.ighorosipov.domain.timer.model.isRunning
import com.ighorosipov.domain.timer.repository.TimerEngine
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TimerEngineImpl @Inject constructor() : TimerEngine {

    private val _states = MutableStateFlow<Map<String, TimerRuntimeState>>(emptyMap())
    override val states: StateFlow<Map<String, TimerRuntimeState>> = _states.asStateFlow()

    override fun snapshot(): List<TimerRuntimeState> = _states.value.values.toList()

    override fun hasRunning(): Boolean = _states.value.values.any { it.status.isRunning() }

    override fun start(id: String, durationMs: Long, name: String, ringtoneUri: String?) {
        _states.update { map ->
            val current = map[id]
            val remaining = when {
                current == null || current.status.isFinished() -> durationMs
                else -> current.remainingMs
            }
            map + (id to TimerRuntimeState(
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
                map + (id to it.copy(remainingMs = durationMs, status = TimerStatus.IDLE))
            } ?: map
        }
    }

    override fun stopAll() {
        _states.update { emptyMap() }
    }

    override fun restore(states: List<TimerRuntimeState>) {
        _states.update { existing ->
            val merged = existing.toMutableMap()
            states.forEach { s -> merged.putIfAbsent(s.id, s) }
            merged
        }
    }

    override fun tick(deltaMs: Long): List<TimerRuntimeState> {
        val finished = mutableListOf<TimerRuntimeState>()
        _states.update { map ->
            map.mapValues { (_, state) ->
                val wasFinished = state.status.isFinished()
                if (!state.status.isRunning() || wasFinished) state
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