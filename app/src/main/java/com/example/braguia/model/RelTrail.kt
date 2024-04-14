package com.example.braguia.model

import com.google.gson.annotations.SerializedName

data class RelTrail(

    @SerializedName("id")
    val id: Int,
    @SerializedName("value")
    val value: String,
    @SerializedName("attrib")
    val attrib: String,

    //TODO foreign key
    @SerializedName("trail")
    val trail: Int


)
