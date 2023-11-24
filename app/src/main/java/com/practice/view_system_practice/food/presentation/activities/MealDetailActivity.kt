package com.practice.view_system_practice.food.presentation.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.practice.view_system_practice.R
import com.practice.view_system_practice.databinding.ActivityMealDetailBinding
import com.practice.view_system_practice.food.presentation.fragments.FoodHomeFragment.Companion.MEAL_ID
import com.practice.view_system_practice.food.presentation.fragments.FoodHomeFragment.Companion.MEAL_NAME
import com.practice.view_system_practice.food.presentation.fragments.FoodHomeFragment.Companion.MEAL_THUMBNAIL
import com.practice.view_system_practice.food.presentation.viewmodels.MealDetailViewModel

class MealDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMealDetailBinding
    private lateinit var mealId: String
    private lateinit var mealName: String
    private lateinit var mealThumbnail: String
    private lateinit var viewModel: MealDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_meal_detail)
        viewModel = ViewModelProvider(this)[MealDetailViewModel::class.java]
        getMealDetailInfoFromIntent()
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewModel.getMealDetail(mealId)
        observeDetailData()

    }

    private fun getMealDetailInfoFromIntent() {
        val intent = intent
        mealId = intent.getStringExtra(MEAL_ID) ?: "0"
        mealName = intent.getStringExtra(MEAL_NAME) ?: "none"
        mealThumbnail = intent.getStringExtra(MEAL_THUMBNAIL) ?: "none"
    }

    private fun observeDetailData() {
        viewModel.mealDetail.observe(this) {
            if (it == null) {
                loading()
            } else {
                response()
            }
        }
    }

    private fun loading() {
        binding.apply {
            progressBar.visibility = View.VISIBLE

            btnFavorite.visibility = View.INVISIBLE
            tvInstructionTitle.visibility = View.INVISIBLE
            tvInstructionContent.visibility = View.INVISIBLE
            tvCategory.visibility = View.INVISIBLE
            tvArea.visibility = View.INVISIBLE
            ivYoutube.visibility = View.INVISIBLE
        }

    }

    private fun response() {
        binding.apply {
            progressBar.visibility = View.INVISIBLE

            btnFavorite.visibility = View.VISIBLE
            tvInstructionTitle.visibility = View.VISIBLE
            tvInstructionContent.visibility = View.VISIBLE
            tvCategory.visibility = View.VISIBLE
            tvArea.visibility = View.VISIBLE
            ivYoutube.visibility = View.VISIBLE
        }
    }
}