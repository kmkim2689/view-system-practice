package com.practice.view_system_practice.coroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.practice.view_system_practice.R
import com.practice.view_system_practice.databinding.ActivityCoroutineBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CoroutineActivity : AppCompatActivity() {
    private var count = 0
    private lateinit var binding: ActivityCoroutineBinding
    companion object {
        const val TAG = "CoroutineActivity"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_coroutine)

        // initial project : 모든 작업을 main thread에서 실행
        binding.btnCount.setOnClickListener {
            binding.tvCount.text = count++.toString()
        }

        binding.btnDownload.setOnClickListener {
            // run on background thread
            CoroutineScope(Dispatchers.IO).launch {
                // long runnign task
                downloadUserData()
            }
        }

    }

    // 시간이 오래 걸리는 작업
    private fun downloadUserData() {
        for (i in 1..200_000) {
            Log.i(TAG, "downloading $i in ${Thread.currentThread().name}")
        }
    }
}