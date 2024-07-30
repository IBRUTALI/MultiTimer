package com.ighorosipov.multitimer.feature.get_sounds.data.repository

import android.content.ContentResolver
import android.provider.MediaStore
import com.ighorosipov.multitimer.feature.get_sounds.domain.model.Sound
import com.ighorosipov.multitimer.feature.get_sounds.domain.repository.SoundRepository
import javax.inject.Inject

class SoundRepositoryImpl @Inject constructor(
    private val contentResolver: ContentResolver
): SoundRepository {

    override suspend fun getSounds(): List<Sound> {
        contentResolver.query(

        )
    }

}