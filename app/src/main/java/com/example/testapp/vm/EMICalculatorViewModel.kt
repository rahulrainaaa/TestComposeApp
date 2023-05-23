package com.example.testapp.vm

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

data class EMI(val pc: Float, val ic: Float, val month: Int, val emi: Float)

@HiltViewModel
class EMICalculatorViewModel @Inject constructor() : ViewModel() {

    private val maxRepaymentMonths = 40 * 12    // max 40 years for loan repayment.

    val emiList = mutableStateListOf<EMI>()
    val totalInterestPaid = mutableStateOf<Int?>(null)
    val totalAmountPaid = mutableStateOf<Int?>(null)

    fun hasValidData(): Boolean {
        return emiList.isNotEmpty() &&
                totalInterestPaid.value != null &&
                totalInterestPaid.value ?: 0 > 0 &&
                totalAmountPaid.value != null &&
                totalAmountPaid.value ?: 0 > 0
    }

    @Throws(EMIException::class)
    fun emiMonthsCalculate(principal: Int, rate: Float, emi: Int) {

        emiList.clear()
        totalAmountPaid.value = null
        totalInterestPaid.value = null

        val list = mutableListOf<EMI>()
        var month = 0
        var p = principal.toFloat()
        while (p > 0) {
            val ic = (p * rate) / 1200
            val pc = emi - ic
            month += 1
            if (month > maxRepaymentMonths) throw EMIException.EMIExceedTimeLimitException()
            list.add(EMI(pc = pc, ic = ic, month = month, emi = pc + ic))
            p -= pc
        }
        emiList.addAll(list)

        var interestSum = 0f
        var emiSum = 0f

        emiList.forEach {
            interestSum += it.ic
            emiSum += it.emi
        }

        totalInterestPaid.value = interestSum.toInt()
        totalAmountPaid.value = emiSum.toInt()

    }

    fun clearAll() {
        emiList.clear()
        totalInterestPaid.value = null
        totalAmountPaid.value = null
    }

}

/**
 * Exceptions pertaining to EMI Calculator.
 */
sealed class EMIException(msg: String) : Throwable(msg) {
    class EMIExceedTimeLimitException : EMIException("The EMI cannot repay loan before 40 yrs of time.")
}