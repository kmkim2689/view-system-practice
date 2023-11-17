package com.practice.view_system_practice.dagger

import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class NickelBatteryModule {
    @Binds
    abstract fun bindNickelBattery(nickelBattery: NickelBattery): Battery
}