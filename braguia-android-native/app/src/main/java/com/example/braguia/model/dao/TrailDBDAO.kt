package com.example.braguia.model.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.braguia.model.TrailDB
import kotlinx.coroutines.flow.Flow


@Dao
interface TrailDBDAO {

    @Upsert
    suspend fun insert(trails: List<TrailDB>)

    @Query("SELECT DISTINCT * FROM trail")
    fun getTrails(): Flow<List<TrailDB>>

    @Query("SELECT * FROM trail WHERE id=:trailId")
    suspend fun getTrail(trailId:Long): TrailDB

    @Query("SELECT DISTINCT * FROM trail WHERE id IN (:trailIds)")
    suspend fun getTrailsByIDs(trailIds:List<Long>) : List<TrailDB>

    @Query("DELETE FROM trail")
    suspend fun deleteAll()
}