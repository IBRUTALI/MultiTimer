package com.ighorosipov.multitimer.domain.use_case

import com.ighorosipov.multitimer.domain.model.Timer
import com.ighorosipov.multitimer.domain.repository.TimerRepository
import com.ighorosipov.multitimer.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AddTimerUseCase @Inject constructor(
    private val repository: TimerRepository
) {

    operator fun invoke(timer: Timer): Flow<Resource<Unit>> {
        return flow {
            emit(Resource.Loading())
            emit(repository.insertTimer(timer = timer))
        }
    }

}