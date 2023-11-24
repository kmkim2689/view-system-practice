package com.practice.view_system_practice.food.presentation.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.practice.view_system_practice.R
import com.practice.view_system_practice.databinding.FragmentFoodHomeBinding
import com.practice.view_system_practice.food.data.model.MealList
import com.practice.view_system_practice.food.data.remote.MealRetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FoodHomeFragment : Fragment() {
    private lateinit var binding: FragmentFoodHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_food_home, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
                        binding.recommendation = randomMealData
                        Log.d(TAG, "${randomMealData.idMeal} : ${randomMealData.strMeal}")
                    } ?: return
                }

                override fun onFailure(call: Call<MealList>, t: Throwable) {
                    Log.d(TAG, t.message.toString())
                }

            })
        } catch (e: Exception) {
            Log.e(TAG, e.localizedMessage ?: "unexpected error")
        }
    }

    companion object {
        const val TAG = "FoodHomeFragment"
    }

}

