package com.practice.view_system_practice.food.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.practice.view_system_practice.R
import com.practice.view_system_practice.databinding.ItemMealBinding
import com.practice.view_system_practice.food.data.model.Meal

class CategoryMealsRVAdapter(
    private val onItemClick: (Meal) -> Unit
) :
    RecyclerView.Adapter<CategoryMealsRVAdapter.CategoryMealsViewHolder>() {

    private var meals = ArrayList<Meal>()

    fun setMeals(meals: ArrayList<Meal>) {
        meals.clear()
        meals.addAll(meals)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryMealsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemMealBinding>(
            layoutInflater,
            R.layout.item_meal,
            parent,
            false
        )

        return CategoryMealsViewHolder(binding).apply {
            binding.root.setOnClickListener {
                onItemClick(meals[adapterPosition])
            }
        }
    }

    override fun onBindViewHolder(holder: CategoryMealsViewHolder, position: Int) {
        holder.bind(meals[position])
    }

    override fun getItemCount(): Int = meals.size

    inner class CategoryMealsViewHolder(val binding: ItemMealBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(meal: Meal) {
                binding.tvMeal.text = meal.strMeal
                Glide.with(binding.ivMeal.context)
                    .load(meal.strMealThumb)
                    .into(binding.ivMeal)
            }
    }
}