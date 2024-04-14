package com.example.braguia.model

import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class AppInfo(

    //val id:Int,
    @SerializedName("appName")
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