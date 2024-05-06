package com.example.braguia.model.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.braguia.model.Preferences
import kotlinx.coroutines.flow.Flow

@Dao
interface PreferencesDAO {

    @Query("SELECT * FROM preferences WHERE username=:username")
    fun getPreferences(username:String): Flow<Preferences?>

    @Upsert
    suspend fun updatePreference(prefereces: Preferences)

}