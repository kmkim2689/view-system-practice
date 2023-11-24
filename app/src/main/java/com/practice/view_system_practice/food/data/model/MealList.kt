package com.practice.view_system_practice.food.data.model


import com.google.gson.annotations.SerializedName

data class MealList(
    @SerializedName("meals")
    val meals: List<Meal>
)