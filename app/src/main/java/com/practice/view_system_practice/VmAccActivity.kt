package com.practice.view_system_practice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.practice.view_system_practice.databinding.ActivityVmAccBinding

class VmAccActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVmAccBinding
    private lateinit var viewModel: VmAccViewModel
    private lateinit var viewModelFactory: VmAccViewModelFactory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_vm_acc)
        // viewmodel 생성자가 없는 경우
        // viewModel = ViewModelProvider(this).get(VmAccViewModel::class.java)

        // viewmodel 생성자가 있는 경우
        viewModelFactory = VmAccViewModelFactory(15)
        viewModel = ViewModelProvider(this, viewModelFactory).get(VmAccViewModel::class.java)

        binding.apply {
            tvCount.text = viewModel.getCountData().toString()

            btnCount.setOnClickListener {
                viewModel.getUpdatedData(etCount.text.toString().toInt())
                tvCount.text = viewModel.getCountData().toString()
                etCount.text = null
            }
        }
    }
}