package com.ighorosipov.domain.stopwatch.use_case

import com.ighorosipov.domain.stopwatch.StopwatchController
import javax.inject.Inject

class StartStopwatchUseCase @Inject constructor(
    private val controller: StopwatchController
) {
    operator fun invoke(
        id: String,
        name: String,
        colorHex: String
    ) = controller.start(id, name, colorHex)
}