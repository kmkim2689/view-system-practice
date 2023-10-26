package com.practice.view_system_practice.unit_tests

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CalcViewModelFactory(
    private val calculations: Calculations
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CalcViewModel::class.java)) {
            return CalcViewModel(calculations) as T
        }
        throw IllegalArgumentException("unknown viewmodel class")
    }
}