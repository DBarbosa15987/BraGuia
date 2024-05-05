package com.example.braguia.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.braguia.model.User

@Dao
interface UserDAO {
    @Query("SELECT * FROM user WHERE username=:username")
    suspend fun getUser(username: String): User

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User)

    @Query("DELETE FROM user WHERE username=:username")
    suspend fun delete(username: String)

}