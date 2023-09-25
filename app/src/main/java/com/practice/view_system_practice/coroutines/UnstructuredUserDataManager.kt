package com.practice.view_system_practice.coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UnstructuredUserDataManager {

    // 값을 제대로 반환하지 못함
    suspend fun getTotalUserCount(): Int {
        var count = 0

        CoroutineScope(Dispatchers.IO).launch {
            delay(1000L)
            count = 50
        }

        return count
    }

    // 값을 제대로 반환
    suspend fun getTotalUserCountAsync(): Int {
        val count = CoroutineScope(Dispatchers.IO).async {
            delay(3000L)
            50
        }

        return count.await()
    }

    suspend fun getTotal(): Int {
        val count = withContext(Dispatchers.IO) {
            delay(1000L)
            50
        }

        return count
    }
}