package com.practice.view_system_practice.clean_architecture_1.data


import com.google.gson.annotations.SerializedName

data class Movie(
    val id: Int,
    @SerializedName("original_language")
    val originalLanguage: String?,
    @SerializedName("overview")
    val overview: String?,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("release_date")
    val releaseDate: String?,
    @SerializedName("title")
    val title: String?,
)