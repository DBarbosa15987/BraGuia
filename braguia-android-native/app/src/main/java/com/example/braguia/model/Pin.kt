package com.example.braguia.model

import com.google.gson.annotations.SerializedName

data class Pin(

    @SerializedName("id")
    val id: Long,

    @SerializedName("pin_name")
    val pinName: String,

    @SerializedName("pin_desc")
    val pinDesc: String,

    @SerializedName("pin_lat")
    val pinLat: Double,

    @SerializedName("pin_lng")
    val pinLng: Double,

    @SerializedName("pin_alt")
    val pinAlt: Double,

    @SerializedName("rel_pin")
    val relPin: List<RelPin>,

    @SerializedName("media")
    val media: List<Media>
)
