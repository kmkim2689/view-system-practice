package com.practice.view_system_practice.food.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class FoodViewModelFactory<VM>(private val viewModel: Class<VM>) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(viewModel)) {
            return modelClass as T
        } else {
            throw IllegalArgumentException("unknown view model class")
        }
    }
}