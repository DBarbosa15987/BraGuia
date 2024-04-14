package com.example.braguia.model

import com.google.gson.annotations.SerializedName

data class Partner(

    @SerializedName("partner_name")
    val partnerName:String,
    @SerializedName("partner_phone")
    val partnerPhone:String,
    @SerializedName("partner_url")
    val partnerUrl:String,
    @SerializedName("partner_mail")
    val partnerMail:String,
    @SerializedName("partner_desc")
    val partnerDesc:String,
    @SerializedName("partner_app")
    val partnerApp:String,


)
