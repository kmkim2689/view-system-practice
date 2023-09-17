package com.practice.view_system_practice

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class VmAccViewModel(startingTotal: Int): ViewModel() {
    private var _count = MutableLiveData<Int>()
    val count: LiveData<Int>
        get() = _count

    var input = MutableLiveData<String>()


    init {
        _count.value = startingTotal
    }

/*    fun getCountData(): Int {
        return count
    }*/

    fun updatedData() {
        input.value?.let {
            _count.value = _count.value?.plus(it.toInt())
        }
    }
}