package com.practice.view_system_practice.coroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.practice.view_system_practice.R
import com.practice.view_system_practice.databinding.ActivityCoroutineBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
/*            CoroutineScope(Dispatchers.IO).launch {
                // long runnign task
                downloadUserData()
            }*/

            // structured concurrency
            CoroutineScope(Dispatchers.Main).launch {
                // binding.tvUserMessage.text = UnstructuredUserDataManager().getTotalUserCount().toString()
                binding.tvUserMessage.text = StructuredUserDataManager().getTotalUserCount().toString()
            }
        }

        lifecycle.coroutineScope.launch {

        }

        lifecycleScope.launch {

        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {

            }
        }
    }

    // 시간이 오래 걸리는 작업
    private suspend fun downloadUserData() {
        for (i in 1..200_000) {
            // Log.i(TAG, "downloading $i in ${Thread.currentThread().name}")

            // IO Thread 상에서 실행하면, App은 crash => calledFromWrongThreadException
            // UI 계층구조를 만든 UI 쓰레드만이 View에 접근할 수 있음
            // context 전환 : withContext()
            // withContext는 suspend function이므로, 다른 suspend function 혹은 coroutine scope 내부에서만 실행 가능
            withContext(Dispatchers.Main) {
                binding.tvUserMessage.text = "downloading $i"
            }
        }
    }
}