package com.practice.view_system_practice.viewmodel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.practice.view_system_practice.R
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
        binding.viewModel = viewModel
        // viewmodel에서 사용할 livedata들이 생명주기를 인식할 수 있도록 하는 작업
        binding.lifecycleOwner = this

        // live data를 xml에 연결하였으므로 (databinding) 생략
/*        binding.apply {
            btnCount.setOnClickListener {
                viewModel.updatedData(etCount.text.toString().toInt())
                etCount.text = null
            }
        }*/

        // live data
/*        viewModel.count.observe(this) {
            binding.tvCount.text = it.toString()
        }*/
    }
}