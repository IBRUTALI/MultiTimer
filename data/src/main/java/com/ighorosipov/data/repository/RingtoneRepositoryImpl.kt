package com.ighorosipov.data.repository

import android.media.RingtoneManager
import com.ighorosipov.domain.model.Ringtone
import com.ighorosipov.domain.repository.RingtoneRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RingtoneRepositoryImpl @Inject constructor(
    private val ringtoneManager: RingtoneManager,
    private val dispatcher: CoroutineDispatcher
): RingtoneRepository {

    override suspend fun getRingtones(): List<Ringtone> {
        return withContext(dispatcher) {
            val cursor = ringtoneManager.cursor

            (0 until cursor.count).map { position ->
                cursor.moveToPosition(position)

                Ringtone(
                    title = cursor.getString(
                        RingtoneManager.TITLE_COLUMN_INDEX
                    ),
                    uri = ringtoneManager
                        .getRingtoneUri(position)
                        .toString()
                )
            }
        }
    }

    override suspend fun findTitleByUri(uri: String?): String? {
        if (uri == null) return null
        return getRingtones().find { it.uri == uri }?.title
    }

}