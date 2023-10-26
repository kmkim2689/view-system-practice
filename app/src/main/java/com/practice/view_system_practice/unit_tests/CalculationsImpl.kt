package com.practice.view_system_practice.unit_tests

import kotlin.math.pow

class CalculationsImpl: Calculations {

    private val pi = 3.14

    override fun calculateCircumference(radius: Double): Double {
        return 2 * pi * radius
    }

    override fun calculateArea(radius: Double): Double {
        return pi * radius.pow(2)
    }
}