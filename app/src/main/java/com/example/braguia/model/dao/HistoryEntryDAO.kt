package com.example.braguia.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.braguia.model.HistoryEntry
import com.example.braguia.model.HistoryEntryDB
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryEntryDAO {

    @Query("SELECT h.username,h.id AS entryId,h.timeStamp,trail.*  FROM history AS h JOIN trail ON h.id=trail.id WHERE username=:username ORDER BY h.timeStamp DESC")
    fun getHistory(username:String): Flow<List<HistoryEntry>>

    @Insert
    suspend fun insert(historyEntryDB: HistoryEntryDB)

}