package com.practice.view_system_practice.dagger

import dagger.Module
import dagger.Provides

@Module
class MemoryCardModule {
    @Provides
    fun provideMemoryCard(): MemoryCard {
        return MemoryCard()
    }
}