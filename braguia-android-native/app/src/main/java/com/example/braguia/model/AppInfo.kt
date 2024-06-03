package com.example.braguia.model

import com.google.gson.annotations.SerializedName


data class AppInfo(

    @SerializedName("app_name")
    val appName:String,
    @SerializedName("app_desc")
    val appDesc:String,
    @SerializedName("socials")
    val socials: List<Social>,
    @SerializedName("contacts")
    val contacts: List<Contact>,
    @SerializedName("partners")
    val partners: List<Partner>,
    @SerializedName("app_landing_page_text")
    val landingPageText:String

)

