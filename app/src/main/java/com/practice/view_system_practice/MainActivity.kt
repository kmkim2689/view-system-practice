package com.practice.view_system_practice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import androidx.databinding.DataBindingUtil
import com.practice.view_system_practice.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val button = binding.btnSubmit

        button.setOnClickListener {
            displayGreeting(binding.editName.text)
        }
    }

    private fun displayGreeting(text: Editable) {
        binding.apply {
            tvGreeting.text = text
        }
    }
}