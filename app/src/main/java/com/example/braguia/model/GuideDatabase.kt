package com.example.braguia.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.braguia.model.dao.AppInfoDBDAO
import com.example.braguia.model.dao.BookmarkDAO
import com.example.braguia.model.dao.ContactDAO
import com.example.braguia.model.dao.EdgeDBDAO
import com.example.braguia.model.dao.MediaDAO
import com.example.braguia.model.dao.PartnerDAO
import com.example.braguia.model.dao.PinDBDAO
import com.example.braguia.model.dao.RelPinDAO
import com.example.braguia.model.dao.RelTrailDAO
import com.example.braguia.model.dao.SocialDAO
import com.example.braguia.model.dao.TrailDBDAO
import com.example.braguia.model.dao.UserDAO
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized


@Database(
    entities = [User::class, AppInfoDB::class, Social::class, Contact::class, Media::class,
        Partner::class, TrailDB::class, PinDB::class, RelPin::class, RelTrail::class, EdgeDB::class,
        Bookmark::class],
    version = 16
)
abstract class GuideDatabase : RoomDatabase() {

    abstract fun bookmarkDAO(): BookmarkDAO
    abstract fun userDAO(): UserDAO
    abstract fun trailDBDAO(): TrailDBDAO
    abstract fun appInfoDAO(): AppInfoDBDAO
    abstract fun contactDAO(): ContactDAO
    abstract fun mediaDAO(): MediaDAO
    abstract fun partnerDAO(): PartnerDAO
    abstract fun socialDAO(): SocialDAO
    abstract fun pinDBDAO(): PinDBDAO
    abstract fun relPinDAO(): RelPinDAO
    abstract fun relTrailDAO(): RelTrailDAO
    abstract fun edgeDBDAO(): EdgeDBDAO

    companion object {
        @Volatile
        private var Instance: GuideDatabase? = null
        private const val DATABASE_NAME: String = "BraGuia"

        @OptIn(InternalCoroutinesApi::class)
        fun getInstance(context: Context): GuideDatabase {

            return Instance ?: synchronized(this) {

                Room.databaseBuilder(context, GuideDatabase::class.java, DATABASE_NAME)
                    //.createFromAsset("database/flight_search.db")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }

            }

        }
    }
}
