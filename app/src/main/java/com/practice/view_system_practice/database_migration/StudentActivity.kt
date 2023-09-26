package com.practice.view_system_practice.database_migration

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.practice.view_system_practice.R
import com.practice.view_system_practice.databinding.ActivityStudentBinding
import kotlinx.coroutines.launch

class StudentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStudentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_student)

        val dao = StudentDatabase.getInstance(this).dao

        val nameText = binding.etName
        val emailText = binding.etEmail
        val courseText = binding.etCourse
        val button = binding.btnSubmit

        button.setOnClickListener {
            lifecycleScope.launch {
                nameText.text.let {
                    dao.insertStudent(Student(0, it.toString(), emailText.text.toString(), courseText.text.toString()))
                    nameText.setText("")
                    emailText.setText("")
                    courseText.setText("")
                }
            }
        }
    }
}