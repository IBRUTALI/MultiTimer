package com.ighorosipov.multitimer.feature.player.domain.use_case

import com.ighorosipov.multitimer.feature.player.domain.repository.PlayerRepository
import javax.inject.Inject

class StopUseCase @Inject constructor(
    private val playerRepository: PlayerRepository
) {
    suspend operator fun invoke() {
        playerRepository.stop()
    }
}