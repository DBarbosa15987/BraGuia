package com.example.braguia.network

import com.google.gson.annotations.SerializedName


data class LoginRequest(
    @SerializedName("username")
    val username: String,
    @SerializedName("email")
    val email: String = "",
    @SerializedName("password")
    val password: String,

)
