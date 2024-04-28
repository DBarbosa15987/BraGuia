package com.example.braguia.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

/*
data class Bookmark(

    @Embedded val user: User,
    @Relation(
        parentColumn = "username",
        entityColumn = "id",
        associateBy = Junction(BookmarkEntryCrossRef::class)
    )
    val trails: List<TrailDB>

)*/
