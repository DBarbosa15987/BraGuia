package com.example.braguia.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.example.braguia.model.RelPin

/*
@Dao
interface RelPinDAO {

    @Upsert()
    suspend fun insert(relPins: List<RelPin>)

    @Query("SELECT DISTINCT * FROM relPin WHERE id = :pinId")
    suspend fun getRelPin(pinId:Long): List<RelPin>

    @Query("DELETE FROM relPin")
    suspend fun deleteAll()

}*/
