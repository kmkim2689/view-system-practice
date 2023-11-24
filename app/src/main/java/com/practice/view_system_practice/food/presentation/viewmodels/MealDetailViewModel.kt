package com.practice.view_system_practice.food.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.practice.view_system_practice.food.data.model.Meal
import com.practice.view_system_practice.food.data.model.MealList
import com.practice.view_system_practice.food.data.remote.MealRetrofitInstance
import com.practice.view_system_practice.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealDetailViewModel : ViewModel() {

    private var _mealDetail = MutableLiveData<Meal>()
    val mealDetail: LiveData<Meal>
        get() = _mealDetail

    fun getMealDetail(id: String) {
        MealRetrofitInstance.api.getMealDetail(id).enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.body() != null) {
                    _mealDetail.value = response.body()!!.meals[0]
                } else return
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("DetailVm", t.message.toString())
            }
        })
    }
}