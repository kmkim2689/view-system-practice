package com.practice.view_system_practice.unit_tests

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.lang.Exception

class CalcViewModel(
    private val calculations: Calculations
): ViewModel() {

    var radius = MutableLiveData<String>()

    private var _area = MutableLiveData<String?>()
    val area: LiveData<String?>
        get() = _area

    private var _circumference = MutableLiveData<String?>()
    val circumference: LiveData<String?>
        get() = _circumference

    fun calculate() {
        try {
            val radiusDouble = radius.value?.toDouble()
            if (radiusDouble != null) {
                calculateArea(radiusDouble)
                calculateCircumference(radiusDouble)
            } else {
                _area.value = null
                _circumference.value = null
            }
        } catch (e: Exception) {
            Log.i("CalcViewModel", e.message.toString())
            _area.value = null
            _circumference.value = null
        }
    }

    fun calculateArea(radius: Double) {
        val calculatedArea = calculations.calculateArea(radius)
        _area.value = calculatedArea.toString()
    }

    fun calculateCircumference(radius: Double) {
        val calculatedCircumference = calculations.calculateCircumference(radius)
        _circumference.value = calculatedCircumference.toString()
    }

}