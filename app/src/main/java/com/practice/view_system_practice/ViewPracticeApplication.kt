package com.practice.view_system_practice

import android.app.Application
import com.practice.view_system_practice.dagger.DaggerSmartPhoneComponent
import com.practice.view_system_practice.dagger.MemoryCardModule
import com.practice.view_system_practice.dagger.SmartPhoneComponent

class ViewPracticeApplication : Application() {

    lateinit var smartPhoneComponent: SmartPhoneComponent
    override fun onCreate() {
        super.onCreate()
        smartPhoneComponent = initDagger()
    }

    private fun initDagger(): SmartPhoneComponent = DaggerSmartPhoneComponent.builder()
        .memoryCardModule(MemoryCardModule(1000))
        .build() // build까지만 진행
}