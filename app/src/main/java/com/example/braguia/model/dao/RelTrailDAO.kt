package com.example.braguia.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.example.braguia.model.RelTrail


@Dao
interface RelTrailDAO {

    @Upsert
    suspend fun insert(relTrails: List<RelTrail>)

    @Query("SELECT DISTINCT * FROM relTrail")
    suspend fun getRelTrail(): List<RelTrail>

    @Query("DELETE FROM relTrail")
    suspend fun deleteAll()


}