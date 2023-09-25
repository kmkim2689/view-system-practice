package com.practice.view_system_practice.coroutines

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class StructuredUserDataManager {
    var count = 0
    lateinit var deferred: Deferred<Int>
    suspend fun getTotalUserCount(): Int {
        // structured concurrency를 구현하기 위하여, CoroutineScope Interface를 활용하지 않는다.
        // coroutineScope suspend function을 활용하여 child scope 만든다.

        // coroutineScope 내부에서 사용되는 builder function의 경우, 별도의 명시가 없으면
        // 호출된 곳의 thread와 같은 곳에서 수행된다.
        // 이 scope의 경우 이 suspend function을 호출하는 coroutine이 main thread 상에서 동작하므로, 기본적으로 main thread에서 실행된다.
        coroutineScope {
            // 백그라운드로 전환
            launch(Dispatchers.IO) {
                delay(1000L)
                count = 50
            }

            deferred = async(Dispatchers.IO) {
                delay(5000L)
                10
            }
        }

        return count + deferred.await()
    }

}