package com.example.braguia.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "appInfo",indices = [Index(value = ["appName"],unique = true)])
data class AppInfoDB(

    @PrimaryKey
    val appName:String,
    val appDesc:String,
    val landingPageText:String

)