package com.example.braguia.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.example.braguia.model.EdgeDB

@Dao
interface EdgeDBDAO {

    @Upsert
    suspend fun insert(edges: List<EdgeDB>)

    @Query("SELECT DISTINCT * FROM edge WHERE edgeTrail=:trailId")
    suspend fun getEdges(trailId:Long): List<EdgeDB>

    @Query("DELETE FROM edge")
    suspend fun deleteAll()

}
