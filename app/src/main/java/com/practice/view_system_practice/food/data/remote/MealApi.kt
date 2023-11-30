package com.practice.view_system_practice.food.data.remote

import com.practice.view_system_practice.food.data.model.CategoryList
import com.practice.view_system_practice.food.data.model.MealsByCategoryList
import com.practice.view_system_practice.food.data.model.MealList
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {
    @GET("random.php")
    fun getRandomMeal(): Call<MealList>

    @GET("lookup.php")
    fun getMealDetail(
        @Query("i") id: String
    ): Call<MealList>

    @GET("filter.php")
    suspend fun getPopularMeals(
        @Query("c") category: String // Seafood
    ): Response<MealsByCategoryList>

    @GET("categories.php")
    suspend fun getCategoryList(): Response<CategoryList>

    @GET("filter.php")
    suspend fun getMealsByCategory(
        @Query("c") category: String // Seafood
    ): Response<MealsByCategoryList>
}