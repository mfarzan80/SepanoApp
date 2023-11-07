package com.sepano.app.ui.calculator

class CalculatorLogic {
    fun performOperation(operand1: Double, operand2: Double, operator: Char): Double {
        return when (operator) {
            '+' -> operand1 + operand2
            '-' -> operand1 - operand2
            '*' -> operand1 * operand2
            '/' -> if (operand2 != 0.0) operand1 / operand2 else Double.NaN
            else -> Double.NaN
        }
    }

}