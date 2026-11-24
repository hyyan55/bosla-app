package com.bosla.template

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bosla.template.databinding.ActivityMainBinding
import java.util.Locale
import kotlin.math.abs

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var operand: Double? = null
    private var pendingOperator: Char? = null
    private var currentInput = ""
    private var freshInput = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val digits = mapOf(
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
        digits.forEach { (button, digit) ->
            button.setOnClickListener { onDigit(digit) }
        }

        binding.btnAdd.setOnClickListener { onOperator('+') }
        binding.btnSub.setOnClickListener { onOperator('-') }
        binding.btnMul.setOnClickListener { onOperator('*') }
        binding.btnDiv.setOnClickListener { onOperator('/') }
        binding.btnClear.setOnClickListener { onClear() }
        binding.btnEquals.setOnClickListener { onEquals() }
    }

    private fun onDigit(digit: String) {
        if (freshInput) {
            currentInput = ""
            freshInput = false
        }
        if (currentInput.length >= 12) return
        if (currentInput == "0") currentInput = ""
        currentInput += digit
        show(currentInput)
    }

    private fun onOperator(op: Char) {
        val value = currentInput.toDoubleOrNull()
        if (value != null) {
            operand = if (operand != null && pendingOperator != null && !freshInput) {
                compute(operand!!, value, pendingOperator!!) ?: run {
                    showError()
                    return
                }
            } else {
                value
            }
        }
        pendingOperator = op
        freshInput = true
    }

    private fun onEquals() {
        val value = currentInput.toDoubleOrNull() ?: return
        val op = pendingOperator ?: return
        val a = operand ?: return
        val result = compute(a, value, op) ?: run {
            showError()
            return
        }
        currentInput = formatResult(result)
        show(currentInput)
        operand = null
        pendingOperator = null
        freshInput = true
    }

    private fun onClear() {
        operand = null
        pendingOperator = null
        currentInput = ""
        freshInput = true
        show("0")
    }

    private fun compute(a: Double, b: Double, op: Char): Double? {
        return when (op) {
            '+' -> a + b
            '-' -> a - b
            '*' -> a * b
            '/' -> if (b == 0.0) null else a / b
            else -> null
        }
    }

    private fun showError() {
        show(getString(R.string.div_by_zero))
        operand = null
        pendingOperator = null
        currentInput = ""
        freshInput = true
    }

    private fun formatResult(value: Double): String {
        if (value.isNaN() || value.isInfinite()) return value.toString()
        if (value % 1.0 == 0.0 && abs(value) < 1e15) return value.toLong().toString()
        return String.format(Locale.US, "%.10f", value).trimEnd('0').trimEnd('.')
    }

    private fun show(text: String) {
        binding.textDisplay.text = text
    }
}
