package com.practice.view_system_practice.coroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.practice.view_system_practice.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CoroutineMission1Activity : AppCompatActivity() {

    companion object {
        const val TAG = "CoroutineMission1Activity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coroutine_mission1)

        CoroutineScope(Dispatchers.Main).launch {
            Log.d(TAG, "thread name : ${Thread.currentThread().name}")
            // main
        }

        CoroutineScope(Dispatchers.IO).launch {
            Log.d(TAG, "thread name : ${Thread.currentThread().name}")
            // DefaultDispatcher-worker-1
            // run in a worker thread
        }

    }
}