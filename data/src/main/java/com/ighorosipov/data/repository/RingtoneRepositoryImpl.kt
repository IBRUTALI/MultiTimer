package com.ighorosipov.data.repository

import android.media.RingtoneManager
import com.ighorosipov.domain.model.Ringtone
import com.ighorosipov.domain.repository.RingtoneRepository
import javax.inject.Inject

class RingtoneRepositoryImpl @Inject constructor(
    private val ringtoneManager: RingtoneManager
): RingtoneRepository {

    override suspend fun getRingtones(): List<Ringtone> {
        val cursor = ringtoneManager.cursor
        return (0 until cursor.count).map {
            cursor.moveToPosition(it)
            Ringtone(
                title = cursor.getString(RingtoneManager.TITLE_COLUMN_INDEX),
                uri = ringtoneManager.getRingtoneUri(it).toString()
            )
        }
    }

    override suspend fun findTitleByUri(uri: String?): String? {
        if (uri == null) return null
        return getRingtones().find { it.uri == uri }?.title
    }

}