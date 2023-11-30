package com.practice.view_system_practice.food.presentation.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.practice.view_system_practice.R
import com.practice.view_system_practice.databinding.FragmentFoodHomeBinding
import com.practice.view_system_practice.food.data.model.Category
import com.practice.view_system_practice.food.data.model.MealsByCategory
import com.practice.view_system_practice.food.presentation.activities.MealDetailActivity
import com.practice.view_system_practice.food.presentation.adapter.MealCategoryRVAdapter
import com.practice.view_system_practice.food.presentation.adapter.PopularMealRVAdapter
import com.practice.view_system_practice.food.presentation.viewmodels.FoodHomeViewModel
import kotlin.collections.ArrayList

class FoodHomeFragment : Fragment() {
    private lateinit var binding: FragmentFoodHomeBinding
    private lateinit var viewModel: FoodHomeViewModel
    private lateinit var popularAdapter: PopularMealRVAdapter
    private lateinit var categoryAdapter: MealCategoryRVAdapter

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
        initPopularMealsRecyclerView()
        initCategoryRecyclerView()
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

    private fun initPopularMealsRecyclerView() {
        popularAdapter = PopularMealRVAdapter(onPopularItemClick)
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        viewModel.popularMeals.observe(viewLifecycleOwner) {
            popularAdapter.setMeals(it as ArrayList<MealsByCategory>)
        }

        binding.rvPopular.apply {
            this.adapter = popularAdapter
            this.layoutManager = layoutManager
        }
    }

    private fun initCategoryRecyclerView() {
        categoryAdapter = MealCategoryRVAdapter()
        val layoutManager = GridLayoutManager(requireContext(), 3)
        viewModel.categories.observe(viewLifecycleOwner) {
            categoryAdapter.setCategories(it as ArrayList<Category>)
        }

        binding.rvCategory.apply {
            this.adapter = categoryAdapter
            this.layoutManager = layoutManager
        }
    }

    private val onPopularItemClick = { popularItem : MealsByCategory ->
        Intent(activity, MealDetailActivity::class.java).let {
            it.putExtra(MEAL_ID, popularItem.idMeal)
            it.putExtra(MEAL_NAME, popularItem.strMeal)
            it.putExtra(MEAL_THUMBNAIL, popularItem.strMealThumb)
            startActivity(it)
        }
    }

    companion object {
        const val TAG = "FoodHomeFragment"
        const val MEAL_ID = "meal_id"
        const val MEAL_NAME = "meal_name"
        const val MEAL_THUMBNAIL = "meal_thumbnail"
    }

}

