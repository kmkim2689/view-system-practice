package com.practice.view_system_practice.unit_tests

import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class CalculationsImplTest {

    private lateinit var calculationsImpl: CalculationsImpl

    @Before
    fun setUp() {
        calculationsImpl = CalculationsImpl()
    }

    @Test
    fun calculateCircumference_radiusGiven_returnsCorrectResult() {
        val result = calculationsImpl.calculateCircumference(2.1)
        // 예상 결과 : 13.188
        assertThat(result).isEqualTo(13.188)
    }

    @Test
    fun calculateCircumference_zeroRadius_returnsCorrectResult() {
        val result = calculationsImpl.calculateCircumference(0.0)
        // 예상 결과 : 13.188
        assertThat(result).isEqualTo(0.0)
    }

    @Test
    fun calculateArea_radiusGiven_returnCorrectResult() {
        val result = calculationsImpl.calculateArea(2.1)
        // 예상 결과 : 13.8474
        assertThat(result).isEqualTo(13.8474)
    }

    @Test
    fun calculateArea_zeroRadius_returnCorrectResult() {
        val result = calculationsImpl.calculateArea(0.0)
        // 예상 결과 : 13.8474
        assertThat(result).isEqualTo(0.0)
    }
}