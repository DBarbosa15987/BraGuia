package com.example.braguia.network

import com.example.braguia.model.Content
import com.example.braguia.model.Trail
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST

interface TrailAPI {


    @GET("trails")
    suspend fun getTrails(): List<Trail>

    @GET("content")
    suspend fun getContent(): List<Content>


}
