package com.ighorosipov.multitimer.feature.get_sounds.domain.repository

import com.ighorosipov.multitimer.feature.get_sounds.domain.model.Sound

interface SoundRepository {

    suspend fun getSounds(): List<Sound>

}