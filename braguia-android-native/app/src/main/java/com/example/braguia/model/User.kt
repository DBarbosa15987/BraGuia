package com.example.braguia.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.google.gson.annotations.SerializedName
import org.jetbrains.annotations.NotNull

@Entity(tableName = "user")
data class User(
    @PrimaryKey
    @SerializedName("username")
    val username: String,
    @SerializedName("user_type")
    val userType: String,
    @SerializedName("last_login")
    val lastLogin: String,
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("last_name")
    val lastName: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("date_joined")
    val dateJoined: String,
    var loggedIn: Boolean = true
)
