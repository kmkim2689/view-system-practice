package com.practice.view_system_practice.dagger

import android.util.Log
import javax.inject.Inject

data class SIMCard @Inject constructor(private val serviceProvider: ServiceProvider) {
    init {
        Log.i("", "Battery Constructed")
    }

    fun getConnection() {
        serviceProvider.getServiceProvider()
    }
}
