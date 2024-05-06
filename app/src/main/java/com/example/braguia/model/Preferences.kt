package com.example.braguia.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "preferences")
data class Preferences(
    @PrimaryKey
    val username:String,
    val notification:Boolean,
    val darkTheme:Boolean
)
