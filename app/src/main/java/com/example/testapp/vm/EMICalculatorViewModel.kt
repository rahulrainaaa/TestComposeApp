package com.example.testapp.vm

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

data class EMI(val pc: Float, val ic: Float, val month: Int, val emi: Float)

@HiltViewModel
class EMICalculatorViewModel @Inject constructor() : ViewModel() {

    @Throws(EMIException::class)
    fun emiMonthsCalculate(principal: Float, rate: Float, emi: Float): List<EMI> {

        val maxRepaymentMonths = 40 * 12
        val emiList = mutableListOf<EMI>()
        var month = 0
        var p = principal
        while (p > 0) {
            val ic = (p * rate) / 1200
            val pc = emi - ic
            month += 1
            if (month > maxRepaymentMonths) throw EMIException.EMIExceedTimeLimitException()
            emiList.add(EMI(pc = pc, ic = ic, month = month, emi = pc + ic))
            p -= pc
        }
        return emiList
    }

}

/**
 * Exceptions pertaining to EMI Calculator.
 */
sealed class EMIException(msg: String) : Throwable(msg) {
    class EMIExceedTimeLimitException : EMIException("The EMI cannot repay loan before 40 yrs of time.")
}