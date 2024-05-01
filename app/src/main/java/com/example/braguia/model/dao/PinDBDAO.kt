package com.example.braguia.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.example.braguia.model.PinDB


@Dao
interface PinDBDAO {

    @Upsert()
    suspend fun insert(pins: List<PinDB>)

    @Query("SELECT DISTINCT * FROM pin WHERE id=:startId OR id=:endId")
    suspend fun getPins(startId:Long,endId:Long): List<PinDB>

    @Query("DELETE FROM pin")
    suspend fun deleteAll()

    @Query("SELECT * FROM pin WHERE id=:pinId")
    suspend fun getPin(pinId:Long): PinDB?

}
