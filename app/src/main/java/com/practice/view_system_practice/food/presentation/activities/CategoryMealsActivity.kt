package com.practice.view_system_practice.food.presentation.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.practice.view_system_practice.R
import com.practice.view_system_practice.databinding.ActivityCategoryMealsBinding
import com.practice.view_system_practice.food.presentation.viewmodels.CategoryMealsViewModel
import com.practice.view_system_practice.food.presentation.viewmodels.FoodViewModelFactory

class CategoryMealsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCategoryMealsBinding
    private lateinit var viewModel: CategoryMealsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_category_meals)
        viewModel =
            ViewModelProvider(this).get(CategoryMealsViewModel::class.java)

    }
}