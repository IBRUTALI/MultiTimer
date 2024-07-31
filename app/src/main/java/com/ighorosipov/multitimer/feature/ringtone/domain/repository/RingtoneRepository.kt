package com.ighorosipov.multitimer.feature.ringtone.domain.repository

import com.ighorosipov.multitimer.feature.ringtone.domain.model.Ringtone

interface RingtoneRepository {

    suspend fun getSounds(): List<Ringtone>

}