package com.example.braguia.repositories

import android.util.Log
import com.example.braguia.model.Content
import com.example.braguia.model.Trail
import com.example.braguia.model.TrailDAO
import com.example.braguia.network.TrailAPI
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response

//TODO
interface TrailRepository {

    suspend fun getTrails(): List<Trail>
    suspend fun getContent(): List<Content>


}

class NetworkTrailRepository(private val trailAPI: TrailAPI) : TrailRepository{
    override suspend fun getTrails(): List<Trail> = trailAPI.getTrails()
    override suspend fun getContent(): List<Content> = trailAPI.getContent()
    }




