package com.example.braguia.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.braguia.model.Contact


@Dao
interface ContactDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(contacts: List<Contact>): List<Long>

    @Query("SELECT DISTINCT * FROM contact WHERE appInfoId = :appInfoId")
    suspend fun getContacts(appInfoId: String): List<Contact>

    @Query("DELETE FROM contact")
    suspend fun deleteAll()


}