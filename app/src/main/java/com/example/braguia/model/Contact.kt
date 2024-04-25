package com.example.braguia.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(
    tableName = "contact",
    indices = [Index(value = ["id"],unique = true),Index(value = ["appInfoId"])],
    foreignKeys = [
        ForeignKey(
            entity = AppInfoDB::class,
            parentColumns = ["appName"],
            childColumns = ["appInfoId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Contact(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id")
    val id:Long = 0,
    @SerializedName("contact_name")
    val contactName:String,
    @SerializedName("contact_phone")
    val contactPhone:String,
    @SerializedName("contact_url")
    val contactUrl:String,
    @SerializedName("contact_mail")
    val contactMail:String,
    @SerializedName("contact_desc")
    val contactDesc:String,
    @SerializedName("contact_app")
    val contactApp:String,
    var appInfoId:String




)
