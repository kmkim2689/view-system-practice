package com.practice.view_system_practice.food.data.model


import com.google.gson.annotations.SerializedName

data class MealsByCategoryList(
    @SerializedName("meals")
    val meals: List<MealsByCategory>
)