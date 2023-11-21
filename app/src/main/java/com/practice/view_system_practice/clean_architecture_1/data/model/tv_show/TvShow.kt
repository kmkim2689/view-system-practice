package com.practice.view_system_practice.clean_architecture_1.data.model.tv_show


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity("popular_tv_shows")
data class TvShow(
    @SerializedName("first_air_date")
    val firstAirDate: String?,
    @PrimaryKey
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String?,
    @SerializedName("overview")
    val overview: String?,
    @SerializedName("poster_path")
    val posterPath: String?,
)