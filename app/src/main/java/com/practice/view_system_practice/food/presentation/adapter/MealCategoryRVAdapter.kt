package com.practice.view_system_practice.food.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.practice.view_system_practice.R
import com.practice.view_system_practice.databinding.ItemFoodCategoryBinding
import com.practice.view_system_practice.food.data.model.Category

class MealCategoryRVAdapter(
    private val onCategoryClick: (Category) -> Unit
) : RecyclerView.Adapter<MealCategoryRVAdapter.MealCategoryViewHolder>() {

    private var categories = ArrayList<Category>()

    fun setCategories(categories: ArrayList<Category>) {
        this.categories.addAll(categories)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealCategoryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemFoodCategoryBinding>(
            layoutInflater,
            R.layout.item_food_category,
            parent,
            false
        )

        return MealCategoryViewHolder(binding).apply {
            binding.root.setOnClickListener {
                onCategoryClick(categories[adapterPosition])
            }
        }
    }

    override fun onBindViewHolder(holder: MealCategoryViewHolder, position: Int) {
        holder.bind(categories[position])
    }

    override fun getItemCount(): Int  = categories.size

    inner class MealCategoryViewHolder(val binding: ItemFoodCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(category: Category) {
            binding.tvCategory.text = category.strCategory
            Glide.with(binding.ivCategory.context)
                .load(category.strCategoryThumb)
                .into(binding.ivCategory)
        }
    }
}