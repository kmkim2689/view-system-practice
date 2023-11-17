package com.practice.view_system_practice.dagger

import android.util.Log
import javax.inject.Inject

class MemoryCard {
    init {
        Log.i("", "Memory Card Constructed")
    }

    fun getSpaceAvailability() {
        Log.i("", "Memory Space available")
    }
}
