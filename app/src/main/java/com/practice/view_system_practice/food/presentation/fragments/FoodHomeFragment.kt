package com.practice.view_system_practice.food.presentation.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.practice.view_system_practice.R
import com.practice.view_system_practice.databinding.FragmentFoodHomeBinding
import com.practice.view_system_practice.food.data.model.Meal
import com.practice.view_system_practice.food.data.model.MealList
import com.practice.view_system_practice.food.data.remote.MealRetrofitInstance
import com.practice.view_system_practice.food.presentation.activities.MealDetailActivity
import com.practice.view_system_practice.food.presentation.viewmodels.FoodHomeViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FoodHomeFragment : Fragment() {
    private lateinit var binding: FragmentFoodHomeBinding
    private lateinit var viewModel: FoodHomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_food_home, container, false)
        viewModel = ViewModelProvider(requireActivity())[FoodHomeViewModel::class.java]
        binding.lifecycleOwner = this
        binding.homeViewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onRandomMealClick()
    }

    private fun onRandomMealClick() {
        binding.cvRandomMeal.setOnClickListener {
            Intent(activity, MealDetailActivity::class.java).also {
                it.putExtra(MEAL_ID, viewModel.randomMeal.value?.idMeal)
                it.putExtra(MEAL_NAME, viewModel.randomMeal.value?.strMeal)
                it.putExtra(MEAL_THUMBNAIL, viewModel.randomMeal.value?.strMealThumb)
                startActivity(it)
            }
        }
    }

    companion object {
        const val TAG = "FoodHomeFragment"
        const val MEAL_ID = "meal_id"
        const val MEAL_NAME = "meal_name"
        const val MEAL_THUMBNAIL = "meal_thumbnail"
    }

}

