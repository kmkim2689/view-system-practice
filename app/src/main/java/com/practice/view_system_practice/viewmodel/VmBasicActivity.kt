package com.practice.view_system_practice.viewmodel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.practice.view_system_practice.R
import com.practice.view_system_practice.databinding.ActivityVmBasicBinding

class VmBasicActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVmBasicBinding
    private lateinit var viewModel: VmBasicViewModel
//    private var count = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_vm_basic)
        viewModel = ViewModelProvider(this).get(VmBasicViewModel::class.java)


/*        binding.apply {
            tvCount.text = count.toString()
            btnCount.setOnClickListener {
                count++
                tvCount.text = count.toString()
            }
        }*/

        viewModel.count.observe(this) {
            binding.tvCount.text = it.toString()
        }

        binding.apply {
            btnCount.setOnClickListener {
                viewModel.updatedCount()
            }
        }

    }
}