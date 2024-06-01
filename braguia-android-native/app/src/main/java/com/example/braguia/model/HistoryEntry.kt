package com.example.braguia.model

import androidx.room.Embedded

data class HistoryEntry(
    val entryId: Long,
    val timeStamp: Long,
    @Embedded
    val trailDB: TrailDB,
    val username: String
)
