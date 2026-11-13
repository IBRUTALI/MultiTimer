package com.ighorosipov.domain.stopwatch.use_case

import com.ighorosipov.domain.stopwatch.StopwatchController
import javax.inject.Inject

class PauseStopwatchUseCase @Inject constructor(
    private val controller: StopwatchController
) {
    operator fun invoke(id: String) = controller.pause(id)
}