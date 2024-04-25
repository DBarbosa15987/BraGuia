package com.example.braguia.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(
    tableName = "partner",
    indices = [Index(value = ["id"],unique = true),Index(value = ["appInfoId"])],
    foreignKeys = [
        ForeignKey(
            entity = AppInfoDB::class,
            parentColumns = ["appName"],
            childColumns = ["appInfoId"],
            onDelete = ForeignKey.CASCADE // Define what to do when the referenced Pin is deleted
        )
    ]
)
data class Partner(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id")
    val id:Long = 0,
    @SerializedName("partner_name")
    val partnerName: String,
    @SerializedName("partner_phone")
    val partnerPhone: String,
    @SerializedName("partner_url")
    val partnerUrl: String,
    @SerializedName("partner_mail")
    val partnerMail: String,
    @SerializedName("partner_desc")
    val partnerDesc: String,
    @SerializedName("partner_app")
    val partnerApp: String,
    var appInfoId: String

)
