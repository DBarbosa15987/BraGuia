package com.example.braguia.repositories

import android.util.Log
import com.example.braguia.model.Content
import com.example.braguia.model.Trail
import com.example.braguia.model.TrailDAO
import com.example.braguia.network.TrailAPI
import androidx.lifecycle.MediatorLiveData;
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response

//Falta encapsular isto tudo no Call
class TrailRepository(val trailAPI: TrailAPI, val trailDAO: TrailDAO) {

    //lateinit var allTrails: MediatorLiveData<List<Trail>>

    suspend fun getTrails(): List<Trail> {

        //var trailList = listOf<Trail>()
        var trailList = trailDAO.getTrails()
        if (trailList.isEmpty()) {
            trailList = trailAPI.getTrails()
            trailDAO.insert(trailList)
        }
        return trailList
    }

    suspend fun getContent(): List<Content> = trailAPI.getContent()

}
