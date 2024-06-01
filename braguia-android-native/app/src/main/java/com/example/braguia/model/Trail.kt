package com.example.braguia.model

import com.google.gson.annotations.SerializedName


data class Trail(

    @SerializedName("id")
    val id: Long,

    @SerializedName("trail_img")
    val trailImg: String,

    @SerializedName("trail_name")
    val trailName: String,

    @SerializedName("trail_desc")
    val trailDesc: String,

    @SerializedName("trail_duration")
    val trailDuration: Int,

    @SerializedName("trail_difficulty")
    val trailDifficulty: String,

    @SerializedName("rel_trail")
    val relTrail: List<RelTrail>,

    @SerializedName("edges")
    val edges: List<Edge>

)

