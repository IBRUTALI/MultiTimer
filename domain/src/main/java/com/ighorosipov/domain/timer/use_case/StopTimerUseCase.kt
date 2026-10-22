package com.ighorosipov.domain.timer.use_case

import com.ighorosipov.domain.timer.TimerController
import javax.inject.Inject

class StopTimerUseCase @Inject constructor(
    private val controller: TimerController
) {
    operator fun invoke(id: String, durationMs: Long) = controller.stop(id, durationMs)
}