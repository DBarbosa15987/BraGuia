package com.example.braguia.model

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.jetbrains.annotations.NotNull

data class Content(
    @SerializedName("id")
    val id: Int,
    @SerializedName("media_file")
    val mediaFile: String,
    @SerializedName("media_type")
    val mediaType: String,
    @SerializedName("media_pin")
    val mediaPin: Int
)
