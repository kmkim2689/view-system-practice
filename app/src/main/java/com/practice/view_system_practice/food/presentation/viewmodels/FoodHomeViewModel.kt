package com.practice.view_system_practice.food.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practice.view_system_practice.food.core.Constants.SEAFOOD
import com.practice.view_system_practice.food.data.model.Category
import com.practice.view_system_practice.food.data.model.MealsByCategory
import com.practice.view_system_practice.food.data.model.Meal
import com.practice.view_system_practice.food.data.model.MealList
import com.practice.view_system_practice.food.data.remote.MealRetrofitInstance
import com.practice.view_system_practice.food.presentation.fragments.FoodHomeFragment
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FoodHomeViewModel : ViewModel() {

    private var _randomMeal = MutableLiveData<Meal>()
    val randomMeal: LiveData<Meal>
        get() = _randomMeal

    private var _popularMeals = MutableLiveData<List<MealsByCategory>>()
    val popularMeals: LiveData<List<MealsByCategory>>
        get() = _popularMeals

    private var _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>>
        get() = _categories

    init {
        getRandomMeal()
        getPopularItems()
        getCategories()
    }

    // private를 붙이지 않으면 ambiguous method 에러 발생 이유?
    private fun getRandomMeal() {
        try {
            val randomMealResponse = MealRetrofitInstance.api.getRandomMeal()
            /*if (randomMealResponse.isSuccessful) {
                randomMealResponse.body()?.let {
                    val randomMealData = randomMealResponse.body()!!.meals[0]
                    Log.d(TAG, "${randomMealData.idMeal} : ${randomMealData.strMeal}")
                } ?: return
            } else {
                Log.e(TAG, randomMealResponse.message())
            }*/
            randomMealResponse.enqueue(object : Callback<MealList> {
                override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                    response.body()?.let {
                        val randomMealData = response.body()!!.meals[0]
                        _randomMeal.value = randomMealData
                    } ?: return
                }

                override fun onFailure(call: Call<MealList>, t: Throwable) {
                    Log.d(FoodHomeFragment.TAG, t.message.toString())
                }

            })
        } catch (e: Exception) {
            Log.e(FoodHomeFragment.TAG, e.localizedMessage ?: "unexpected error")
        }
    }

    private fun getPopularItems() {
        viewModelScope.launch {
            try {
                val popularItemsResponse = MealRetrofitInstance.api.getPopularMeals(SEAFOOD)
                if (popularItemsResponse.isSuccessful) {
                    popularItemsResponse.body()?.let {
                        _popularMeals.value = it.meals
                    }
                    Log.d(FoodHomeFragment.TAG, popularItemsResponse.body().toString())

                    // Log.d(FoodHomeFragment.TAG, popularMeals.value.toString())
                } else {
                    Log.d(FoodHomeFragment.TAG, popularItemsResponse.message())
                }
            } catch (e: Exception) {
                Log.e(FoodHomeFragment.TAG, e.localizedMessage ?: "unexpected error")
            }
        }
    }

    private fun getCategories() {
        viewModelScope.launch {
            try {
                val categoriesResponse = MealRetrofitInstance.api.getCategoryList()
                if (categoriesResponse.isSuccessful) {
                    categoriesResponse.body()?.let {
                        _categories.value = it.categories
                    }
                    Log.d(FoodHomeFragment.TAG, "${categories.value}")

                } else {
                    Log.d(FoodHomeFragment.TAG, categoriesResponse.message())
                }
            } catch (e: Exception) {
                Log.e(FoodHomeFragment.TAG, e.localizedMessage ?: "unexpected error")
            }
        }
    }
}