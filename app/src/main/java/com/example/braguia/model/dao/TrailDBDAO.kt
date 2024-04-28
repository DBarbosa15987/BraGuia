package com.example.braguia.model.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.braguia.model.TrailDB
import kotlinx.coroutines.flow.Flow


@Dao
interface TrailDBDAO {

    @Upsert()
    suspend fun insert(cats: List<TrailDB>)

    //TODO talvez não seja preciso o suspend quando usar o Flow/LiveData
    @Query("SELECT DISTINCT * FROM trail")
    //suspend fun getTrails(): List<TrailDB>
    fun getTrails(): Flow<List<TrailDB>>

    @Query("DELETE FROM trail")
    suspend fun deleteAll()
}