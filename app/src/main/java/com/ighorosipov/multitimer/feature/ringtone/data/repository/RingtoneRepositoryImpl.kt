package com.ighorosipov.multitimer.feature.ringtone.data.repository

import android.media.RingtoneManager
import com.ighorosipov.multitimer.feature.ringtone.domain.model.Ringtone
import com.ighorosipov.multitimer.feature.ringtone.domain.repository.RingtoneRepository
import javax.inject.Inject

class RingtoneRepositoryImpl @Inject constructor(
    private val ringtoneManager: RingtoneManager
): RingtoneRepository {

    override suspend fun getSounds(): List<Ringtone> {
        val cursor = ringtoneManager.cursor
        return (0 until cursor.count).map {
            cursor.moveToPosition(it)
            Ringtone(
                title = cursor.getString(RingtoneManager.TITLE_COLUMN_INDEX),
                uri = ringtoneManager.getRingtoneUri(it).toString()
            )
        }
    }

}