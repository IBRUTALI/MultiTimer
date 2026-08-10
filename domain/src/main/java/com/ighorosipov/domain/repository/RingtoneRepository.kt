package com.ighorosipov.domain.repository

import com.ighorosipov.domain.model.Ringtone

interface RingtoneRepository {
    suspend fun getSounds(): List<Ringtone>
}