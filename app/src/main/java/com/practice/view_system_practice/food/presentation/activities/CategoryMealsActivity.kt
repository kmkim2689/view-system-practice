package com.practice.view_system_practice.food.presentation.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.practice.view_system_practice.R
import com.practice.view_system_practice.databinding.ActivityCategoryMealsBinding
import com.practice.view_system_practice.food.data.model.Meal
import com.practice.view_system_practice.food.presentation.adapter.CategoryMealsRVAdapter
import com.practice.view_system_practice.food.presentation.fragments.FoodHomeFragment
import com.practice.view_system_practice.food.presentation.fragments.FoodHomeFragment.Companion.CATEGORY_NAME
import com.practice.view_system_practice.food.presentation.viewmodels.CategoryMealsViewModel
import com.practice.view_system_practice.food.presentation.viewmodels.FoodViewModelFactory

class CategoryMealsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCategoryMealsBinding
    private lateinit var viewModel: CategoryMealsViewModel
    private lateinit var adapter: CategoryMealsRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_category_meals)
        viewModel = ViewModelProvider(this).get(CategoryMealsViewModel::class.java)

        getCategoryMeals()
        initMealRecyclerView()
    }

    private fun getCategoryMeals() {
        val category = intent.getStringExtra(CATEGORY_NAME)
        category?.let {
            viewModel.getMealsByCategory(it)
            binding.tvCategory.text = it
        }
    }

    private fun initMealRecyclerView() {
        adapter = CategoryMealsRVAdapter(onItemClick)
        val layoutManager = GridLayoutManager(this, 2)
        val meals = viewModel.meals

        meals.observe(this) {
            adapter.setMeals(it as ArrayList<Meal>)
        }

        binding.rvMeals.apply {
            this.adapter = this@CategoryMealsActivity.adapter
            this.layoutManager = layoutManager
        }
    }

    private val onItemClick = { meal: Meal ->
        Intent(this, MealDetailActivity::class.java).let {
            it.putExtra(FoodHomeFragment.MEAL_ID, meal.idMeal)
            it.putExtra(FoodHomeFragment.MEAL_NAME, meal.strMeal)
            it.putExtra(FoodHomeFragment.MEAL_THUMBNAIL, meal.strMealThumb)
            startActivity(it)
        }
    }
}