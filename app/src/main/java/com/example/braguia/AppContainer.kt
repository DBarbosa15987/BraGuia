package com.example.braguia

import android.content.Context
import android.webkit.CookieManager
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.braguia.model.GuideDatabase
import com.example.braguia.network.API
import com.example.braguia.repositories.AppInfoRepository
import com.example.braguia.repositories.MediaRepository
import com.example.braguia.repositories.PinRepository
import com.example.braguia.repositories.PreferencesRepository
import com.example.braguia.repositories.TrailRepository
import com.example.braguia.repositories.UserRepository
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


private const val LAYOUT_PREFERENCE_NAME = "layout_preferences"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = LAYOUT_PREFERENCE_NAME
)

interface AppContainer {
    val trailRepository: TrailRepository
    val pinRepository: PinRepository
    val mediaRepository: MediaRepository
    val appInfoRepository: AppInfoRepository
    val userRepository: UserRepository
    val preferencesRepository: PreferencesRepository
}

class BraGuiaAppContainer(
    private val context: Context
) : AppContainer {
    private val baseUrl = "http://192.168.85.186"
    //private val baseUrl = "https://d57ab7b839dd953d5546cec45b5f5a25.serveo.net/"

    /**
     * Use the Retrofit builder to build a retrofit object using a kotlinx.serialization converter
     */
    private fun createRetrofit(): Retrofit {
        val client = OkHttpClient.Builder()
            .cookieJar(object : CookieJar {
                override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
                    val cookieManager = CookieManager.getInstance()
                    cookieManager.removeAllCookies(null)
                    for (cookie in cookies) {
                        val cookieString =
                            cookie.name + "=" + cookie.value + "; path=" + cookie.path
                        cookieManager.setCookie(cookie.domain, cookieString)
                    }
                    // saves cookies to persistent storage
                    cookieManager.flush()
                }

                override fun loadForRequest(url: HttpUrl): List<Cookie> {
                    val cookieManager = CookieManager.getInstance()
                    val cookies: MutableList<Cookie> = ArrayList()
                    if (cookieManager.getCookie(url.toString()) != null) {
                        val splitCookies =
                            cookieManager.getCookie(url.toString()).split("[,;]".toRegex())
                                .dropLastWhile { it.isEmpty() }
                                .toTypedArray()
                        for (i in splitCookies.indices) {
                            Cookie.parse(url, splitCookies[i].trim { it <= ' ' })
                                ?.let { cookies.add(it) }
                        }
                    }
                    return cookies
                }
            })
            .addInterceptor(HttpLoggingInterceptor().apply {
                this.level = HttpLoggingInterceptor.Level.HEADERS
            })
            .build()
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

    override val preferencesRepository: PreferencesRepository by lazy {
        PreferencesRepository(context.dataStore)
    }

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
        UserRepository(
            retrofitService,
            database.userDAO(),
            database.bookmarkDAO(),
            database.historyEntryDAO()
        )
    }
}
