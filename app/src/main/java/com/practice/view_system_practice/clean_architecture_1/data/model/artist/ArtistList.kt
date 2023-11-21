package com.practice.view_system_practice.clean_architecture_1.data.model.artist


import com.google.gson.annotations.SerializedName

data class ArtistList(
    @SerializedName("results")
    val results: List<Artist>
)