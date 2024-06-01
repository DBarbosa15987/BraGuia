package com.example.braguia.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.example.braguia.model.PinDB


@Dao
interface PinDBDAO {

    @Upsert
    suspend fun insert(pins: List<PinDB>)

    @Query("DELETE FROM pin")
    suspend fun deleteAll()

    @Query("SELECT * FROM pin WHERE id=:pinId")
    suspend fun getPin(pinId:Long): PinDB?

    @Query("SELECT * FROM pin")
    suspend fun getAllPins(): List<PinDB>
}
