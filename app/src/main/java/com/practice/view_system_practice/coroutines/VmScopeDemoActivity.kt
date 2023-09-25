package com.practice.view_system_practice.coroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.practice.view_system_practice.R

class VmScopeDemoActivity : AppCompatActivity() {

    private lateinit var viewModel: VmScopeDemoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vm_scope_demo)
        viewModel = ViewModelProvider(this).get(VmScopeDemoViewModel::class.java)

        viewModel.users.observe(this) { users ->
            users?.forEach {
                // recyclerView 작업하기
            }
        }
    }
}