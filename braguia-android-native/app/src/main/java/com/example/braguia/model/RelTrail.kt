package com.example.braguia.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(
    tableName = "relTrail",
    indices = [Index(value = ["id"],unique = true), Index(value = ["trail"])],
    foreignKeys = [
        ForeignKey(
            entity = TrailDB::class,
            parentColumns = ["id"],
            childColumns = ["trail"],
            onDelete = ForeignKey.CASCADE // Define what to do when the referenced Pin is deleted
        )
    ]
)
data class RelTrail(
    @PrimaryKey
    @SerializedName("id")
    val id: Long,
    @SerializedName("value")
    val value: String,
    @SerializedName("attrib")
    val attrib: String,
    @SerializedName("trail")
    val trail: Long
)
