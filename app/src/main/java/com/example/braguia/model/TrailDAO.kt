package com.example.braguia.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface TrailDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cats: List<Trail>)

    //TODO talvez não seja preciso o suspend quando usar o Flow/LiveData
    @Query("SELECT DISTINCT * FROM trail")
    suspend fun getTrails(): List<Trail>

    @Query("DELETE FROM trail")
    suspend fun deleteAll()


}