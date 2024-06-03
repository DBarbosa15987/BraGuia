package com.example.braguia.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.braguia.model.Social

@Dao
interface SocialDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cats: List<Social>): List<Long>

    @Query("SELECT DISTINCT * FROM social WHERE appInfoId = :appInfoId")
    suspend fun getSocials(appInfoId:String): List<Social>

    @Query("DELETE FROM social")
    suspend fun deleteAll()

}