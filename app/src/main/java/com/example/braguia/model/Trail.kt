package com.example.braguia.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "trail",indices = [Index(value = ["id"],unique = true)])
data class Trail(

    @PrimaryKey
    @SerializedName("id")
    //@ColumnInfo(name = "id")
    val id: Int,

    @SerializedName("trail_img")
    //@ColumnInfo(name = "trail_img")
    val trailImg: String,

    @SerializedName("trail_name")
    val trailName: String,

    @SerializedName("trail_desc")
    val trailDesc: String,

    @SerializedName("trail_duration")
    val trailDuration: Int,

    @SerializedName("trail_difficulty")
    val trailDifficulty: String,

    //@SerializedName("rel_trail")
    //val relTrail: List<>,

/*    @SerializedName("edges")
    val edges: List<Edge>*/


    )

/*
data class RelTrail(
    val id: Int,
    val value: String,
    val attrib: String,
    val trail: Int
)

data class Media(
    val id: Int,
    val media_file: String,
    val media_type: String,
    val media_pin: Int
)

data class RelPin(
    val id: Int,
    val value: String,
    val attrib: String,
    val pin: Int
)

data class EdgeStart(
    val id: Int,
    val rel_pin: List<RelPin>,
    val media: List<Media>,
    val pin_name: String,
    val pin_desc: String,
    val pin_lat: Double,
    val pin_lng: Double,
    val pin_alt: Double
)

data class EdgeEnd(
    val id: Int,
    val rel_pin: List<RelPin>,
    val media: List<Media>,
    val pin_name: String,
    val pin_desc: String,
    val pin_lat: Double,
    val pin_lng: Double,
    val pin_alt: Double
)

data class Edge(
    val id: Int,
    val edge_start: EdgeStart,
    val edge_end: EdgeEnd,
    val edge_transport: String,
    val edge_duration: Int,
    val edge_desc: String,
    val edge_trail: Int
)

@Entity(tableName = "trail",indices = [Index(value = ["id"],unique = true)])
data class Trail(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    val id: String,

    @SerializedName("trail_img")
    @ColumnInfo(name = "trail_img")
    val trail_img: String,

    @Transient
    @SerializedName("rel_trail")
    val rel_trail: List<RelTrail>? = null,

    @Transient
    @SerializedName("edges")
    val edges: List<Edge>? = null
)*/

/*
    {

   @Override
    public int hashCode() {
        return Objects.hash(id, image_url);


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Trail

        if (id != other.id) return false
        if (trail_img != other.trail_img) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + trail_img.hashCode()
        return result
    }
}*/
