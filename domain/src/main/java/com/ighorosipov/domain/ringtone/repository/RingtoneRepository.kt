package com.ighorosipov.domain.ringtone.repository

import com.ighorosipov.domain.ringtone.model.Ringtone

interface RingtoneRepository {

    suspend fun getRingtones(): List<Ringtone>

    suspend fun findTitleByUri(uri: String?): String?

}