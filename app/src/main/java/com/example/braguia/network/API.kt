package com.example.braguia.network

import com.example.braguia.model.AppInfo
import com.example.braguia.model.Trail
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface API {

    @GET("trails")
    suspend fun getTrails(): List<Trail>

    @GET("app")
    suspend fun getAppInfo(): List<AppInfo>

    @POST("login")
    fun login(@Body loginRequest:LoginRequest): Call<ResponseBody>

}
