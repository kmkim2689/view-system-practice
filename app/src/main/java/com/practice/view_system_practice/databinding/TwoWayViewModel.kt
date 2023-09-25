package com.practice.view_system_practice.databinding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TwoWayViewModel: ViewModel() {

    val userName = MutableLiveData<String>()

    init {
        userName.value = "frank"
    }
}