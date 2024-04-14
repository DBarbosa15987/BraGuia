package com.example.braguia.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "RelPin")
data class RelPin(

    @PrimaryKey
    @SerializedName("id")
    @ColumnInfo(name = "id")
    val id:Int,
    @SerializedName("value")
    @ColumnInfo(name = "value")
    val value:String,
    @SerializedName("attrib")
    @ColumnInfo(name = "attrib")
    val attrib:String,

    //TODO isto aqui é uma foreign key
    @SerializedName("pin")
    val pin:Int

)
