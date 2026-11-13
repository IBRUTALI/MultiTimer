package com.ighorosipov.data.stopwatch.repository

import com.ighorosipov.domain.stopwatch.model.Lap
import com.ighorosipov.domain.stopwatch.model.Stopwatch
import com.ighorosipov.domain.stopwatch.model.StopwatchStatus
import com.ighorosipov.domain.stopwatch.repository.StopwatchEngine
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StopwatchEngineImpl @Inject constructor() : StopwatchEngine {

    private val _states = MutableStateFlow<Map<String, Stopwatch>>(emptyMap())
    override val states = _states.asStateFlow()

    override fun snapshot(): List<Stopwatch> = _states.value.values.toList()

    override fun hasRunning(): Boolean = _states.value.values.any { it.isRunning }

    override fun start(id: String, name: String, colorHex: String) {
        _states.update { map ->
            val current = map[id]
            val elapsed = current?.elapsedMs ?: 0L
            map + (id to Stopwatch(
                id = id,
                name = name,
                elapsedMs = elapsed,
                colorHex = colorHex,
                status = StopwatchStatus.RUNNING,
                laps = current?.laps ?: emptyList()
            ))
        }
    }

    override fun pause(id: String) {
        _states.update { map ->
            map[id]?.let { state ->
                if (state.isRunning) {
                    map + (id to state.copy(status = StopwatchStatus.PAUSED))
                } else map
            } ?: map
        }
    }

    override fun resume(id: String) {
        _states.update { map ->
            map[id]?.let { state ->
                if (state.isPaused) {
                    map + (id to state.copy(status = StopwatchStatus.RUNNING))
                } else map
            } ?: map
        }
    }

    override fun stop(id: String) {
        _states.update { map ->
            map[id]?.let { state ->
                map + (id to state.copy(
                    elapsedMs = 0L,
                    status = StopwatchStatus.IDLE,
                    laps = emptyList()
                ))
            } ?: map
        }
    }

    override fun addLap(id: String) {
        _states.update { map ->
            val current = map[id] ?: return@update map
            if (!current.isRunning) return@update map

            val previousTotal = current.laps.lastOrNull()?.totalTimeMs ?: 0L
            val newLap = Lap(
                index = current.laps.size + 1,
                splitTimeMs = current.elapsedMs - previousTotal,
                totalTimeMs = current.elapsedMs
            )
            map + (id to current.copy(laps = current.laps + newLap))
        }
    }

    override fun stopAll() {
        _states.update { emptyMap() }
    }

    override fun restore(states: List<Stopwatch>) {
        _states.update { existing ->
            val merged = existing.toMutableMap()
            states.forEach { s -> merged.putIfAbsent(s.id, s) }
            merged
        }
    }

    override fun tick(deltaMs: Long) {
        _states.update { map ->
            map.mapValues { (_, state) ->
                if (state.isRunning) {
                    state.copy(elapsedMs = state.elapsedMs + deltaMs)
                } else state
            }
        }
    }
}