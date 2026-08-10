package com.ighorosipov.domain.use_case

import com.ighorosipov.domain.model.Timer
import com.ighorosipov.domain.repository.TimerRepository
import com.ighorosipov.utils.Resource
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