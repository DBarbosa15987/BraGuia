package com.example.braguia.model

import com.google.gson.annotations.SerializedName

data class Edge(

    @SerializedName("id")
    val id:Long,
    @SerializedName("edge_transport")
    val edgeTransport:String,
    @SerializedName("edge_duration")
    val edgeDuration:Int,
    @SerializedName("edge_desc")
    val edgeDesc:String,
    @SerializedName("edge_trail")
    val edgeTrail:Long,
    @SerializedName("edge_start")
    val edgeStart: Pin,
    @SerializedName("edge_end")
    val edgeEnd: Pin

)
