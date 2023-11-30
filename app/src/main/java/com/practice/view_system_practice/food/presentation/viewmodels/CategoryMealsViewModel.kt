package com.practice.view_system_practice.food.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practice.view_system_practice.food.data.model.MealList
import com.practice.view_system_practice.food.data.model.MealsByCategory
import com.practice.view_system_practice.food.data.remote.MealRetrofitInstance
import com.practice.view_system_practice.retrofit.RetrofitInstance
import kotlinx.coroutines.launch

class CategoryMealsViewModel : ViewModel() {

    private var _meals = MutableLiveData<List<MealsByCategory>>()
    val meals: LiveData<List<MealsByCategory>>
        get() = _meals

    fun getMealsByCategory(categoryName: String) {
        try {
            viewModelScope.launch {
                val response = MealRetrofitInstance.api.getMealsByCategory(categoryName)
                if (response.isSuccessful) {
                    response.body()?.let {
                        _meals.value = it.meals
                    }
                    Log.e("CategoryMealsViewModel", response.body()?.meals.toString())
                } else {
                    Log.e("CategoryMealsViewModel", response.message())
                }
            }
        } catch (e: Exception) {
            Log.e("CategoryMealsViewModel", e.message.toString())
        }
    }
}