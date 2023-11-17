package com.practice.view_system_practice.dagger

import android.util.Log
import javax.inject.Inject

data class SmartPhone @Inject constructor(
    val battery: Battery,
    val simCard: SIMCard,
    val memoryCard: MemoryCard
) {
    // 주 생성자를 살펴보면, SmartPhone 클래스는 3개의 다른 클래스에 직접적으로 의존하고 있음을 알 수 있음

    init {
        battery.getPower()
        simCard.getConnection()
        memoryCard.getSpaceAvailability()
//        Log.i()
    }

    fun makeACallWithRecording() {
        Log.i("", "Calling...")
    }
}
