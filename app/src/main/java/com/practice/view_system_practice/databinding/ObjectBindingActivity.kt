package com.practice.view_system_practice.databinding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.practice.view_system_practice.R

class ObjectBindingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityObjectBindingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_object_binding)
        binding.student = getStudent()

/*        val student = getStudent()

        binding.apply {
            tvName.text = student.name
            tvEmail.text = student.email
        }*/
    }

    private fun getStudent(): Student {
        return Student(1, "Alex", "alex@gmail.com")
    }
}

data class Student(
    val id: Int,
    val name: String,
    val email: String
)