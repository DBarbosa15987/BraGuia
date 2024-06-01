package com.example.braguia.model.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.braguia.model.User

@Dao
interface UserDAO {
    @Query("SELECT * FROM user WHERE username=:username")
    suspend fun getUser(username: String): User
}