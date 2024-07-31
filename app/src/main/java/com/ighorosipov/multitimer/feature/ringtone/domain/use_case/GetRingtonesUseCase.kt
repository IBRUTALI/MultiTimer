package com.ighorosipov.multitimer.feature.ringtone.domain.use_case

import com.ighorosipov.multitimer.feature.ringtone.domain.repository.RingtoneRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetRingtonesUseCase @Inject constructor(
    private val ringtoneRepository: RingtoneRepository,
) {

    suspend operator fun invoke() = flow {
        emit(ringtoneRepository.getSounds())
    }

}