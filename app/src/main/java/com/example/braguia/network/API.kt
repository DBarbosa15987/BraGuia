package com.example.braguia.network

import com.example.braguia.model.AppInfo
import com.example.braguia.model.Media
import com.example.braguia.model.Trail
import retrofit2.http.GET

interface TrailAPI {

    @GET("trails")
    suspend fun getTrails(): List<Trail>

    @GET("app")
    suspend fun getAppInfo(): List<AppInfo>

}
