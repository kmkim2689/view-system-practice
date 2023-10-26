package com.practice.view_system_practice.unit_tests

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.practice.view_system_practice.R
import com.practice.view_system_practice.databinding.ActivityCalcBinding

class CalcActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCalcBinding
    private lateinit var viewModel: CalcViewModel
    lateinit var factory: CalcViewModelFactory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_calc)
        factory = CalcViewModelFactory(CalculationsImpl())
        viewModel = ViewModelProvider(this, factory)[CalcViewModel::class.java]

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

    }
}