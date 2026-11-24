package com.ighorosipov.core_presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameNanos
import kotlin.math.abs

@Composable
fun rememberSmoothElapsedMs(
    isRunning: Boolean,
    engineElapsedMs: Long
): Long {
    if (!isRunning) return engineElapsedMs

    var displayedMs by remember { mutableLongStateOf(engineElapsedMs) }
    var lastFrameTime by remember { mutableLongStateOf(System.currentTimeMillis()) }

    LaunchedEffect(Unit) {
        if (isRunning) {
            while (true) {
                withFrameNanos {
                    val now = System.currentTimeMillis()
                    val delta = now - lastFrameTime
                    lastFrameTime = now
                    if (delta < 1000) {
                        displayedMs += delta
                    }
                }
            }
        }
    }

    LaunchedEffect(engineElapsedMs) {
        val diff = abs(engineElapsedMs - displayedMs)
        if (diff > 100) {
            displayedMs = engineElapsedMs
        }
    }

    return displayedMs
}