package com.example.braguia.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "appInfo",indices = [Index(value = ["id"],unique = true)])
data class AppInfo(

    @ColumnInfo("id")
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    @SerializedName("app_name")
    val appName:String,
    @SerializedName("socials")
    val socials: List<Social>,
    @SerializedName("contacts")
    val contacts: List<Contact>,
    @SerializedName("partners")
    val partners: List<Partner>,
    @SerializedName("app_desc")
    val appDesc:String,
    @SerializedName("app_landing_page_text")
    val landingPageText:String

)