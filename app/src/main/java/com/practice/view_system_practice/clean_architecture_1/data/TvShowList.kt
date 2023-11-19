package com.practice.view_system_practice.clean_architecture_1.data


import com.google.gson.annotations.SerializedName

data class TvShowList(
    @SerializedName("results")
    val results: List<TvShow>
)