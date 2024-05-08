package com.example.braguia.model.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.braguia.model.Bookmark
import com.example.braguia.model.TrailDB
import kotlinx.coroutines.flow.Flow

@Dao
interface BookmarkDAO {
    @Query("SELECT trail.* FROM bookmark JOIN trail ON bookmark.trailId=trail.id WHERE bookmark.username=:username ORDER BY bookmark.id")
    fun getBookmarksFromUser(username:String) : Flow<List<TrailDB>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(bookmark: Bookmark)

    @Query("DELETE FROM bookmark WHERE trailId=:trailId AND username=:username")
    suspend fun delete(username:String,trailId:Long)

    @Query("DELETE FROM bookmark WHERE username=:username")
    suspend fun deleteAllFromUser(username: String)

}