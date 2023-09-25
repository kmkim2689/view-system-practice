package com.practice.view_system_practice.coroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.practice.view_system_practice.R
import com.practice.view_system_practice.coroutines.ParallelDecompositionActivity.Companion.TAG
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ParallelDecompositionActivity : AppCompatActivity() {

    companion object {
        const val TAG = "ParallelDecompositionActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parallel_decomposition)

        // bad practice - launch builder
        CoroutineScope(Dispatchers.IO).launch {
            // sequential decomposition : 순차적으로 구성하는 것
            // 2개의 suspend function을 하나씩 실행 -> 사실상 동기적으로 실행하는 것
            // 이것의 문제점 : 동시에 진행하지 못하여 두 태스크를 수행하는 데 18초가 고스란히 사용된다.
            Log.i(TAG, "calculation started")
            val stock1 = getStock1()
            val stock2 = getStock2()
            val total = stock1 + stock2
            Log.i(TAG, "total is $total")

            // 로그를 찍어보면, stock1에 대한 결과가 먼저 찍혀나오고, 그 다음 stock2가 찍히는 것을 확인 가능
        }

        // good practice - async builder
        CoroutineScope(Dispatchers.IO).launch {
            // parallel decomposition
            //
            Log.i(TAG, "calculation started")
            val stock1 = async { getStock1() }
            val stock2 = async { getStock2() }
            val total = stock1.await() + stock2.await()
            Log.i(TAG, "total is $total")

            // sequential decompositon과는 달리, stock2에 대한 결과가 먼저 찍혀나오고 2초 뒤 stock1이 찍혀나옴
            // 10초 소요
        }

        // good practice - async builder -> async in another context
        CoroutineScope(Dispatchers.Main).launch {
            // parallel decomposition
            Log.i(TAG, "calculation started")
            val stock1 = async(Dispatchers.IO) { getStock1() }
            val stock2 = async(Dispatchers.IO) { getStock2() }
            val total = stock1.await() + stock2.await()
            // ui 작업 수행(main thread)
            Toast.makeText(applicationContext, "total is $total", Toast.LENGTH_SHORT).show()
            Log.i(TAG, "total is $total")
        }
    }

    private suspend fun getStock1(): Int {
        delay(10000L)
        Log.i(TAG, "stock 1 returned")
        return 5
    }
    private suspend fun getStock2(): Int {
        delay(8000L)
        Log.i(TAG, "stock 2 returned")
        return 10
    }
}

