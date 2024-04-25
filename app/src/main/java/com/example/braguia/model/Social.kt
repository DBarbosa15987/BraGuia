package com.example.braguia.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(
    tableName = "social",
    indices = [Index(value = ["id"],unique = true),Index(value = ["appInfoId"])],
    foreignKeys = [
        ForeignKey(
            entity = AppInfoDB::class,
            parentColumns = ["appName"],
            childColumns = ["appInfoId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Social(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id")
    val id:Long = 0,
    @SerializedName("social_name")
    val socialName:String,
    @SerializedName("social_url")
    val socialUrl:String,
    @SerializedName("social_share_link")
    val socialShareLink:String,
    @SerializedName("social_app")
    val socialApp:String,
    var appInfoId: String


)
