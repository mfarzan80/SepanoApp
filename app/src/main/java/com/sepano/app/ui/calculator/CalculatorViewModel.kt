package com.sepano.app.ui.calculator


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CalculatorViewModel : ViewModel() {
    private val calculatorLogic = CalculatorLogic()

    val display = MutableLiveData("0")
    val pendingDisplay = MutableLiveData<String>()
    private var operand1: Double? = null
    private var operand2: Double? = null
    private var pendingOperation: Char? = null

    fun onDigitPressed(digit: Char) {
        if (isError())
            onClear()
        if (display.value == "0") {
            display.value = ""
        }
        display.value = (display.value ?: "") + digit
    }

    fun onClear() {
        display.value = "0"
        pendingDisplay.value = ""
        operand1 = null
        operand2 = null
        pendingOperation = null
    }

    private fun isError(): Boolean {
        return display.value == "Error" || display.value == "Nan"
    }

    fun onOperatorPressed(operator: Char) {
        if (isError())
            onClear()
        try {
            val value = display.value?.toDouble()
            if (operand1 == null) {
                operand1 = value
            } else if (pendingOperation != null) {
                if(value != 0.0) {
                    operand2 = value
                    operand2?.let {
                        operand1 =
                            calculatorLogic.performOperation(operand1!!, it, pendingOperation!!)
                    }
                    display.value = operand1?.toNumString()
                }

            }
            display.value = "0"
            pendingOperation = operator
            pendingDisplay.value = "${operand1?.toNumString()} $operator"
        } catch (e: NumberFormatException) {
            display.value = "Error"
        }
    }

    fun onEqualsPressed() {
        if (operand1 == null || pendingOperation == null) return
        operand2 = display.value?.toDouble()

        pendingDisplay.value = "${operand1?.toNumString()} $pendingOperation ${operand2?.toNumString()} ="

        operand2?.let {
            val result = calculatorLogic.performOperation(operand1!!, it, pendingOperation!!)
            display.value = result.toNumString()
            operand1 = result
        }
        pendingOperation = null
    }
}

fun Double.toNumString():String{
    return if(this.toInt().toDouble() == this){
        this.toInt().toString();
    }else{
        this.toString();
    }
}