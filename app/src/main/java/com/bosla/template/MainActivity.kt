package com.bosla.template

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bosla.template.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var currentInput = ""
    private var firstOperand: Double? = null
    private var currentOperator: String? = null
    private var isNewOperation = true
    private var isErrorState = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupNumericButtons()
        setupOperatorButtons()
        setupControlButtons()
    }

    private fun setupNumericButtons() {
        val numberButtons = mapOf(
            binding.btn0 to "0",
            binding.btn1 to "1",
            binding.btn2 to "2",
            binding.btn3 to "3",
            binding.btn4 to "4",
            binding.btn5 to "5",
            binding.btn6 to "6",
            binding.btn7 to "7",
            binding.btn8 to "8",
            binding.btn9 to "9"
        )

        for ((button, value) in numberButtons) {
            button.setOnClickListener {
                appendDigit(value)
            }
        }

        binding.btnDot.setOnClickListener {
            appendDot()
        }
    }

    private fun setupOperatorButtons() {
        val operators = mapOf(
            binding.btnAdd to "+",
            binding.btnSubtract to "-",
            binding.btnMultiply to "×",
            binding.btnDivide to "÷"
        )

        for ((button, op) in operators) {
            button.setOnClickListener {
                setOperator(op)
            }
        }

        binding.btnEquals.setOnClickListener {
            calculateResult()
        }
    }

    private fun setupControlButtons() {
        binding.btnClear.setOnClickListener {
            clearAll()
        }

        binding.btnDelete.setOnClickListener {
            deleteLastChar()
        }
    }

    private fun appendDigit(digit: String) {
        if (isErrorState) {
            clearAll()
        }

        if (isNewOperation) {
            currentInput = if (digit == "0") "0" else digit
            isNewOperation = false
        } else {
            if (currentInput == "0") {
                currentInput = digit
            } else {
                if (currentInput.length < 15) {
                    currentInput += digit
                }
            }
        }
        updateDisplay(currentInput)
    }

    private fun appendDot() {
        if (isErrorState) {
            clearAll()
        }

        if (isNewOperation) {
            currentInput = "0."
            isNewOperation = false
        } else if (!currentInput.contains(".")) {
            currentInput = if (currentInput.isEmpty()) "0." else "$currentInput."
        }
        updateDisplay(currentInput)
    }

    private fun setOperator(op: String) {
        if (isErrorState) return

        if (currentInput.isNotEmpty()) {
            val inputValue = currentInput.toDoubleOrNull()
            if (inputValue != null) {
                if (firstOperand != null && currentOperator != null && !isNewOperation) {
                    val intermediateResult = performCalculation(firstOperand!!, inputValue, currentOperator!!)
                    if (intermediateResult == null) {
                        showError(getString(R.string.error_divide_by_zero))
                        return
                    }
                    firstOperand = intermediateResult
                    updateDisplay(formatResult(intermediateResult))
                } else {
                    firstOperand = inputValue
                }
            }
        }
        currentOperator = op
        isNewOperation = true
    }

    private fun calculateResult() {
        if (isErrorState || firstOperand == null || currentOperator == null || isNewOperation) {
            return
        }

        val secondOperand = currentInput.toDoubleOrNull() ?: return
        val result = performCalculation(firstOperand!!, secondOperand, currentOperator!!)

        if (result == null) {
            showError(getString(R.string.error_divide_by_zero))
        } else {
            currentInput = formatResult(result)
            updateDisplay(currentInput)
            firstOperand = null
            currentOperator = null
            isNewOperation = true
        }
    }

    private fun performCalculation(operand1: Double, operand2: Double, operator: String): Double? {
        return when (operator) {
            "+" -> operand1 + operand2
            "-" -> operand1 - operand2
            "×" -> operand1 * operand2
            "÷" -> {
                if (operand2 == 0.0) null else operand1 / operand2
            }
            else -> null
        }
    }

    private fun deleteLastChar() {
        if (isErrorState) {
            clearAll()
            return
        }

        if (isNewOperation) return

        if (currentInput.isNotEmpty()) {
            currentInput = currentInput.dropLast(1)
            if (currentInput.isEmpty() || currentInput == "-") {
                currentInput = "0"
                isNewOperation = true
            }
            updateDisplay(currentInput)
        }
    }

    private fun clearAll() {
        currentInput = "0"
        firstOperand = null
        currentOperator = null
        isNewOperation = true
        isErrorState = false
        updateDisplay("0")
    }

    private fun showError(message: String) {
        isErrorState = true
        currentInput = ""
        firstOperand = null
        currentOperator = null
        binding.tvDisplay.text = message
    }

    private fun updateDisplay(value: String) {
        binding.tvDisplay.text = value
    }

    private fun formatResult(result: Double): String {
        return if (result % 1.0 == 0.0) {
            if (result > Long.MAX_VALUE.toDouble() || result < Long.MIN_VALUE.toDouble()) {
                result.toString()
            } else {
                result.toLong().toString()
            }
        } else {
            result.toString()
        }
    }
}
