package com.practice.view_system_practice.dagger

import dagger.Component

@Component(
    modules = [
        MemoryCardModule::class
    ]
)
interface SmartPhoneComponent {
    fun getSmartPhone(): SmartPhone
}