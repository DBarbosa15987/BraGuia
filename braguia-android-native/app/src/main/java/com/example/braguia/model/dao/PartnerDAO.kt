package com.example.braguia.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.braguia.model.Partner

@Dao
interface PartnerDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(partners: List<Partner>): List<Long>

    @Query("SELECT DISTINCT * FROM partner WHERE appInfoId = :appInfoId")
    suspend fun getPartner(appInfoId:String): List<Partner>

    @Query("DELETE FROM partner")
    suspend fun deleteAll()
}
