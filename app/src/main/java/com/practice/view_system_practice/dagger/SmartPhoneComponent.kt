package com.practice.view_system_practice.dagger

import com.practice.view_system_practice.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        MemoryCardModule::class,
        NickelBatteryModule::class
    ]
)
interface SmartPhoneComponent {
//    fun getSmartPhone(): SmartPhone

    fun inject(daggerPracticeActivity: DaggerPracticeActivity)
}