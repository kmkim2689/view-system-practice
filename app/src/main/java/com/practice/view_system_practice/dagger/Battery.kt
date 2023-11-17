package com.practice.view_system_practice.dagger

import android.util.Log
import javax.inject.Inject

class Battery @Inject constructor() {
    init {
        Log.i("", "Battery Constructed")
    }

    fun getPower() {
        Log.i("", "Battery power is available")
    }
}
