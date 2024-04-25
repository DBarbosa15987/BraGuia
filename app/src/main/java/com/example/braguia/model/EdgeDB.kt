package com.example.braguia.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(
    tableName = "edge",
    indices = [Index(
        value = ["id"],
        unique = true
    ), Index(value = ["edgeTrail"]), Index(value = ["edgeStart"]), Index(value = ["edgeEnd"])],
    foreignKeys = [
        ForeignKey(
            entity = TrailDB::class,
            parentColumns = ["id"],
            childColumns = ["appInfoId"],
            onDelete = ForeignKey.CASCADE // Define what to do when the referenced Pin is deleted
        ),
        ForeignKey(
            entity = PinDB::class,
            parentColumns = ["id"],
            childColumns = ["edgeStart"],
            onDelete = ForeignKey.CASCADE // Define what to do when the referenced Pin is deleted
        ),
        ForeignKey(
            entity = PinDB::class,
            parentColumns = ["id"],
            childColumns = ["edgeEnd"],
            onDelete = ForeignKey.CASCADE // Define what to do when the referenced Pin is deleted
        )
    ]
)
data class EdgeDB(

    @PrimaryKey
    val id: Long,
    val edgeTransport: String,
    val edgeDuration: Int,
    val edgeDesc: String,
    val edgeTrail: Long,
    val edgeStart: Long,
    val edgeEnd: Long,

    )
