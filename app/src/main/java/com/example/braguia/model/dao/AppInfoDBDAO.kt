package com.example.braguia.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.braguia.model.AppInfoDB
import com.example.braguia.model.Contact
import com.example.braguia.model.Social

@Dao
interface AppInfoDBDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(appInfo: AppInfoDB)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun fullInsert(appInfo: AppInfoDB, contacts: List<Contact>, socials: List<Social>) // ??

    @Query("SELECT DISTINCT * FROM appInfo")
    suspend fun getAppInfo(): List<AppInfoDB>

    //TODO Provelmente vai faltar apagar os contacts e socials. Usar o @delete
    @Query("DELETE FROM appInfo")
    suspend fun deleteAll()

/*    @Transaction
    suspend fun insertAppInfos(appInfos: List<AppInfo>) {
        // Start a transaction
        withContext(Dispatchers.IO) {
            appInfos.forEach { appInfo ->
                insert(appInfo)
            }
        }
    }*/

}