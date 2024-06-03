package com.example.braguia.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(
    tableName = "relPin",
    indices = [Index(value = ["id"],unique = true), Index(value = ["pinId"])],
    foreignKeys = [
        ForeignKey(
            entity = PinDB::class,
            parentColumns = ["id"],
            childColumns = ["pinId"],
            onDelete = ForeignKey.CASCADE // Define what to do when the referenced Pin is deleted
        )
    ]
)
data class RelPin(

    @PrimaryKey
    @SerializedName("id")
    val id:Int,
    @SerializedName("value")
    val value:String,
    @SerializedName("attrib")
    val attrib:String,
    @SerializedName("pin")
    val pinId:Long

)
