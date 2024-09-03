package com.ighorosipov.multitimer.feature.player.domain.use_case

import com.ighorosipov.multitimer.feature.player.domain.Player
import javax.inject.Inject

class StopUseCase @Inject constructor(
    private val player: Player
) {
    suspend operator fun invoke() {
        player.stop()
    }
}