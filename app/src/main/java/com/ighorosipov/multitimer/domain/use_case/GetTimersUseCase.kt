package com.ighorosipov.multitimer.domain.use_case

import com.ighorosipov.multitimer.domain.model.Timer
import com.ighorosipov.multitimer.domain.repository.TimerRepository
import com.ighorosipov.multitimer.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetTimersUseCase @Inject constructor(
    private val repository: TimerRepository
) {

    operator fun invoke(): Flow<Resource<List<Timer>>> {
        return flow {
            emit(Resource.Loading())
            emit(repository.getTimers())
        }
    }

}