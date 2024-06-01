package com.example.braguia.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "history",
    indices = [Index(
        value = ["id"],
        unique = true
    ), Index(value = ["trailId"]), Index(value = ["username"])],
    foreignKeys = [
        ForeignKey(
            entity = TrailDB::class,
            parentColumns = ["id"],
            childColumns = ["trailId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = User::class,
            parentColumns = ["username"],
            childColumns = ["username"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class HistoryEntryDB(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val timeStamp: Long = System.currentTimeMillis(),
    val trailId: Long,
    val username: String
)
