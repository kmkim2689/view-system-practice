package com.practice.view_system_practice.databinding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.practice.view_system_practice.R

class TwoWayActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTwoWayBinding
    private val viewModel: TwoWayViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_two_way)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }
}