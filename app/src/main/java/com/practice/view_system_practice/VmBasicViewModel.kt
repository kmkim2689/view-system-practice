package com.practice.view_system_practice

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class VmBasicViewModel: ViewModel() {
    private var _count = MutableLiveData<Int>(0)
    val count: LiveData<Int>
        get() = _count


    // 현재의 값에서 1을 추가하고 그 값을 return하는 function
    fun updatedCount() {
        _count.value = _count.value?.plus(1)
    }
}