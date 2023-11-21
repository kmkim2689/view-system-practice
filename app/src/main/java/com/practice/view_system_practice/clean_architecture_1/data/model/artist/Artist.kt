package com.practice.view_system_practice.clean_architecture_1.data.model.artist


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "popular_artists")
data class Artist(
    @PrimaryKey
    @SerializedName("id")
    val id: Int,
//    @ColumnInfo(name = "artist_name")
    @SerializedName("name")
    val name: String?,
    @SerializedName("original_name")
    val originalName: String?,
    @SerializedName("popularity")
    val popularity: Double?,
    @SerializedName("profile_path")
    val profilePath: String?
)