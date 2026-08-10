package com.ighorosipov.domain.use_case

import com.ighorosipov.domain.model.Timer
import com.ighorosipov.domain.repository.TimerRepository
import com.ighorosipov.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetTimerByIdUseCase @Inject constructor(
    private val repository: TimerRepository
) {

    operator fun invoke(timerId: String): Flow<Resource<Timer>> {
        return flow {
            emit(Resource.Loading())
            emit(repository.getTimerById(timerId = timerId))
        }
    }

}