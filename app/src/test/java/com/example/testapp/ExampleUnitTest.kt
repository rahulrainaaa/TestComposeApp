package com.example.testapp

import com.example.testapp.vm.EMICalculatorViewModel
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class EMICalculatorViewModelTest {

    val vm = EMICalculatorViewModel()

    @Test
    fun test_emiMonthsCalculate() {
        val emiList = vm.emiMonthsCalculate(30000f, 7f, 30000f)
        emiList.forEach { println(it) }
        assertTrue(emiList.size == 2)
    }
}