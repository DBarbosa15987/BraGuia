package com.example.braguia.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "trail", indices = [Index(value = ["id"], unique = true)])
data class TrailDB(

    @PrimaryKey
    val id: Long,
    val trailImg: String,
    val trailName: String,
    val trailDesc: String,
    val trailDuration: Int,
    val trailDifficulty: String,

    )