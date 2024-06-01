package com.example.braguia.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.braguia.model.AppInfoDB
import com.example.braguia.model.Contact
import com.example.braguia.model.Social
import kotlinx.coroutines.flow.Flow

@Dao
interface AppInfoDBDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(appInfo: AppInfoDB)

    @Query("SELECT DISTINCT * FROM appInfo")
    suspend fun getAppInfo(): List<AppInfoDB>

    @Query("DELETE FROM appInfo")
    suspend fun deleteAll()


}