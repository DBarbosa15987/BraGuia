package com.example.braguia

import android.content.Context
import com.example.braguia.model.GuideDatabase
import com.example.braguia.network.API
import com.example.braguia.repositories.AppInfoRepository
import com.example.braguia.repositories.MediaRepository
import com.example.braguia.repositories.PinRepository
import com.example.braguia.repositories.TrailRepository
import com.example.braguia.repositories.UserRepository
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


interface AppContainer {
    val trailRepository: TrailRepository
    val pinRepository: PinRepository
    val mediaRepository: MediaRepository
    val appInfoRepository: AppInfoRepository
    val userRepository: UserRepository
}


class BraGuiaAppContainer(val context: Context) : AppContainer {
    private val baseUrl = "https://579f8a2e08b3ae26545741d09e8f230a.serveo.net/"

    /**
     * Use the Retrofit builder to build a retrofit object using a kotlinx.serialization converter
     */


    val client = OkHttpClient().newBuilder()

    private val retrofit: Retrofit =
        Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    private val retrofitService: API by lazy {
        retrofit.create(API::class.java)
    }

    private val database: GuideDatabase by lazy { GuideDatabase.getInstance(context) }

    override val mediaRepository: MediaRepository by lazy {
        MediaRepository(database.mediaDAO())
    }
    override val pinRepository: PinRepository by lazy {
       PinRepository(database.pinDBDAO(),database.relPinDAO(),mediaRepository)
    }

    override val trailRepository: TrailRepository by lazy {
        TrailRepository(retrofitService,database.trailDBDAO(), database.edgeDBDAO(),database.relTrailDAO(),pinRepository)
    }

    override val appInfoRepository: AppInfoRepository by lazy {
        AppInfoRepository(
            retrofitService,
            database.appInfoDAO(),
            database.socialDAO(),
            database.contactDAO(),
            database.partnerDAO()
        )
    }

    override val userRepository: UserRepository by lazy {
        UserRepository(retrofitService)
    }


}
