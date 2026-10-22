package com.ighorosipov.domain.timer.use_case

import com.ighorosipov.domain.timer.TimerController
import javax.inject.Inject

class PauseTimerUseCase @Inject constructor(
    private val controller: TimerController
) {
    operator fun invoke(id: String) = controller.pause(id)
}