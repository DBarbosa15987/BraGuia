package com.example.braguia

import android.content.Context
import android.webkit.CookieManager
import com.example.braguia.model.GuideDatabase
import com.example.braguia.network.API
import com.example.braguia.repositories.AppInfoRepository
import com.example.braguia.repositories.MediaRepository
import com.example.braguia.repositories.PinRepository
import com.example.braguia.repositories.TrailRepository
import com.example.braguia.repositories.UserRepository
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
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

class BraGuiaAppContainer(private val context: Context) : AppContainer {
    private val baseUrl = "http://29a644fa4087557d586fc409f92e75a1.serveo.net/"

    /**
     * Use the Retrofit builder to build a retrofit object using a kotlinx.serialization converter
     */
    private fun createRetrofit(): Retrofit {
        val client = OkHttpClient.Builder()
            .cookieJar(object : CookieJar {
                private val cookieStore = HashMap<HttpUrl?, List<Cookie>>()
                private val cookieManager = CookieManager.getInstance()

                override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
                    val host = url.host
                    cookieStore[host.toHttpUrlOrNull()] = cookies
                    cookieManager.removeAllCookies(null)
                    val cookies1 = cookieStore[host.toHttpUrlOrNull()]
                    if (cookies1 != null) {
                        for (cookie in cookies1) {
                            val cookieString =
                                cookie.name + "=" + cookie.value + "; path=" + cookie.path
                            cookieManager.setCookie(cookie.domain, cookieString)
                        }
                    }
                }

                override fun loadForRequest(url: HttpUrl): List<Cookie> {
                    val host = url.host
                    val cookies = cookieStore[host.toHttpUrlOrNull()]
                    return cookies ?: ArrayList()
                }
            }).build()

        CookieManager.getInstance().setAcceptCookie(true)
        return Retrofit.Builder().baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val retrofit: Retrofit = createRetrofit()

    private val retrofitService: API by lazy {
        retrofit.create(API::class.java)
    }

    private val database: GuideDatabase by lazy { GuideDatabase.getInstance(context) }

    override val mediaRepository: MediaRepository by lazy {
        MediaRepository(database.mediaDAO())
    }
    override val pinRepository: PinRepository by lazy {
        PinRepository(database.pinDBDAO(), database.relPinDAO(), mediaRepository)
    }

    override val trailRepository: TrailRepository by lazy {
        TrailRepository(
            retrofitService,
            database.trailDBDAO(),
            database.edgeDBDAO(),
            database.relTrailDAO(),
            pinRepository
        )
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
