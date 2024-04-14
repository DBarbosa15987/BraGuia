package com.example.braguia

import com.example.braguia.network.TrailAPI
import com.example.braguia.repositories.NetworkTrailRepository
import com.example.braguia.repositories.TrailRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


interface AppContainer {
    val trailRepository: TrailRepository
}


class BraGuiaAppContainer(/*private val context: Context*/) : AppContainer {
    private val baseUrl = "https://c14d-193-137-92-5.ngrok-free.app/"

    /**
     * Use the Retrofit builder to build a retrofit object using a kotlinx.serialization converter
     */

    private val retrofit: Retrofit =
        Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    private val retrofitService: TrailAPI by lazy {
        retrofit.create(TrailAPI::class.java)
    }

    override val trailRepository: TrailRepository by lazy {
        NetworkTrailRepository(retrofitService)
    }
}
