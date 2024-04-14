package com.example.braguia.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "Pin")
data class Pin(

    @PrimaryKey
    @SerializedName("id")
    @ColumnInfo(name = "id")
    val id:Int,

    @SerializedName("pin_name")
    @ColumnInfo(name = "pin_name")
    val pinName:String,

    @SerializedName("pin_desc")
    @ColumnInfo(name = "pin_desc")
    val pinDesc:String,

    @SerializedName("pin_lat")
    @ColumnInfo(name = "pin_lat")
    val pinLat:Float,

    @SerializedName("pin_lng")
    @ColumnInfo(name = "pin_lng")
    val pinLng:Float,

    @SerializedName("pin_alt")
    @ColumnInfo(name = "pin_alt")
    val pinAlt:Float,

/*    //TODO
    @SerializedName("rel_pin")
    val relPin:List<RelPin>,

    @SerializedName("media")
    val media: List<Content>*/
)
