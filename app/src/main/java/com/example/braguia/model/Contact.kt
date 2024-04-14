package com.example.braguia.model

import com.google.gson.annotations.SerializedName

data class Contact(

    @SerializedName("contact_name")
    val contactName:String,
    @SerializedName("contact_phone")
    val contactPhone:String,
    @SerializedName("contact_url")
    val contactUrl:String,
    @SerializedName("contact_mail")
    val contactMail:String,
    @SerializedName("contact_desc")
    val contactDesc:String,
    @SerializedName("contact_app")
    val contactApp:String,


)
