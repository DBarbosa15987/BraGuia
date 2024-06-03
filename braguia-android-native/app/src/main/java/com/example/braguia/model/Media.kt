package com.example.braguia.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(
    tableName = "media",
    indices = [Index(value = ["id"],unique = true),Index(value = ["mediaPin"])],
    foreignKeys = [
        ForeignKey(
            entity = PinDB::class,
            parentColumns = ["id"],
            childColumns = ["mediaPin"],
            onDelete = ForeignKey.CASCADE // Define what to do when the referenced Pin is deleted
        )
    ]
)
data class Media(

    @PrimaryKey
    @SerializedName("id")
    val id: Int,
    @SerializedName("media_file")
    val mediaFile: String,
    @SerializedName("media_type")
    val mediaType: String,
    @SerializedName("media_pin")
    val mediaPin: Int
)
