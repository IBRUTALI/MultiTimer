package com.ighorosipov.domain.repository

import com.ighorosipov.domain.model.Ringtone

interface RingtoneRepository {

    suspend fun getRingtones(): List<Ringtone>

    suspend fun findTitleByUri(uri: String?): String?

}