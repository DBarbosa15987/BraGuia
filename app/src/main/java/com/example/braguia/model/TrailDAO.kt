package com.example.braguia.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface TrailDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cats: List<Trail>)

    @Query("SELECT DISTINCT * FROM trail")
    fun getTrails(): List<Trail>

    @Query("DELETE FROM trail")
    fun deleteAll()


}