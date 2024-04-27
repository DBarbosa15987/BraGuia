package com.example.braguia.repositories

import com.example.braguia.model.Media
import com.example.braguia.model.dao.MediaDAO


class MediaRepository(
    val mediaDAO: MediaDAO,
) {
    suspend fun insert(media: List<Media>) {
        mediaDAO.insert(media)
    }

    suspend fun getMedia(pinId: Long): List<Media> {
        return mediaDAO.getMedia(pinId)
    }
}
