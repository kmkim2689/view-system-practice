package com.practice.view_system_practice.dagger

import android.util.Log
import javax.inject.Inject

class ServiceProvider @Inject constructor() {
    init {
        Log.i("", "ServiceProvider constructed")
    }

    fun getServiceProvider() {
        Log.i("", "Service Provider Connected")
    }
}