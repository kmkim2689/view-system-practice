package com.practice.view_system_practice.unit_tests

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

class CalcViewModelTest {

    private lateinit var calcViewModel: CalcViewModel
    private lateinit var calculations: Calculations

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        // mockito를 활용하여 인스턴스를 만든다
        // mockito를 활용하여 테스트에 대한 정답을 미리 산출해놓을 수 있음
        calculations = Mockito.mock(Calculations::class.java)
        // kotlin에 이미 when 기능이 존재하기 때문에 mockito의 when을 이용하기 위하여 backtick 활용
        // when(실제결괏값).thenReturn(예상치) => 일치 여부를 확인
        Mockito.`when`(calculations.calculateArea(2.1)).thenReturn(13.8474)
        calcViewModel = CalcViewModel(calculations)
    }

    @Test
    fun calculateArea_radiusSent_updateLiveData() {
        calcViewModel.calculateArea(2.1)
        val result = calcViewModel.area.value
        assertThat(result).isEqualTo("13.8474")
    }
}