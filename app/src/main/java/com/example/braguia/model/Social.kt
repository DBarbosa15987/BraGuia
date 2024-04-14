package com.example.braguia.model

import com.google.gson.annotations.SerializedName

data class Social(

    @SerializedName("social_name")
    val socialName:String,
    @SerializedName("social_url")
    val socialUrl:String,
    @SerializedName("social_share_link")
    val socialShareLink:String,
    @SerializedName("social_app")
    val socialApp:String,


)
