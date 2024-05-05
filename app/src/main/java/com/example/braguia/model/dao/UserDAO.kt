package com.example.braguia.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.braguia.model.User

@Dao
interface UserDAO {
    @Query("SELECT * FROM user WHERE username=:username")
    suspend fun getUser(username: String): User

    @Query("SELECT * FROM user WHERE loggedIn=1")
    suspend fun getLoggedInUser(): List<User>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User)

    @Update
    suspend fun updateLoggedIn(user: User)

    @Query("DELETE FROM user WHERE username=:username")
    suspend fun delete(username: String)

}