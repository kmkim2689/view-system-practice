package com.practice.view_system_practice.food.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.practice.view_system_practice.R
import com.practice.view_system_practice.databinding.ItemPopularBinding
import com.practice.view_system_practice.food.data.model.MealsByCategory
import java.util.ArrayList

class PopularMealRVAdapter(
    private val onItemClick: (MealsByCategory) -> Unit
) : RecyclerView.Adapter<PopularMealRVAdapter.PopularMealViewHolder>() {

    private var mealsList = ArrayList<MealsByCategory>()

    fun setMeals(meals: ArrayList<MealsByCategory>) {
        mealsList.clear()
        mealsList.addAll(meals)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMealViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemPopularBinding>(
            layoutInflater,
            R.layout.item_popular,
            parent,
            false
        )

        return PopularMealViewHolder(binding).apply {
            binding.root.setOnClickListener {
                onItemClick(mealsList[adapterPosition])
            }
        }
    }

    override fun getItemCount(): Int = mealsList.size

    override fun onBindViewHolder(holder: PopularMealViewHolder, position: Int) {
        holder.bind(mealsList[position])
    }

    inner class PopularMealViewHolder(val binding: ItemPopularBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(popularMeal: MealsByCategory) {
            Glide.with(binding.ivPopularMeal.context)
                .load(popularMeal.strMealThumb)
                .into(binding.ivPopularMeal)
        }

    }
}
