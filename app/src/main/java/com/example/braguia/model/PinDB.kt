package com.example.braguia.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "Pin",indices = [Index(value = ["id"],unique = true)])
data class PinDB(

    @PrimaryKey
    val id:Long,
    val pinName:String,
    val pinDesc:String,
    val pinLat:Float,
    val pinLng:Float,
    val pinAlt:Float,

)
