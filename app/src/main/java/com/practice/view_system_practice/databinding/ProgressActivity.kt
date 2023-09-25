package com.practice.view_system_practice.databinding

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.practice.view_system_practice.R

class ProgressActivity: AppCompatActivity() {

    private lateinit var binding: ActivityProgressBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_progress)

        val button = binding.btnSubmit
        button.setOnClickListener {
            if (binding.progressCircular.visibility == View.GONE) {
                binding.progressCircular.visibility = View.VISIBLE
                binding.btnSubmit.text = "Stop"
            } else if (binding.progressCircular.visibility == View.VISIBLE) {
                binding.progressCircular.visibility = View.GONE
                binding.btnSubmit.text = "Start"
            }
        }
    }
}