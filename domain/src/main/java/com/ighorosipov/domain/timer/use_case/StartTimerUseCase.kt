package com.ighorosipov.domain.timer.use_case

import com.ighorosipov.domain.timer.TimerController
import javax.inject.Inject

class StartTimerUseCase @Inject constructor(
    private val controller: TimerController
) {
    operator fun invoke(
        id: String,
        durationMs: Long,
        name: String,
        ringtoneUri: String?
    ) =
        controller.start(id, durationMs, name, ringtoneUri)
}