package com.practice.view_system_practice.dagger

import android.util.Log
import javax.inject.Inject

class NickelBattery @Inject constructor() : Battery {
    override fun getPower() {
        Log.i("", "Power from nickel")
    }
}