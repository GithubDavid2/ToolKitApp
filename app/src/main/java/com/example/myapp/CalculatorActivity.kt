package com.example.myapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class CalculatorActivity : AppCompatActivity() {

    private lateinit var resultTextView: TextView
    private var operand1: Double = 0.0
    private var operand2: Double = 0.0
    private var operator: String? = null
    private var resultCalculated: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculator)

        resultTextView = findViewById(R.id.resultTextView)

        val button0 = findViewById<Button>(R.id.button0)
        val button1 = findViewById<Button>(R.id.button1)
        val button2 = findViewById<Button>(R.id.button2)
        val button3 = findViewById<Button>(R.id.button3)
        val button4 = findViewById<Button>(R.id.button4)
        val button5 = findViewById<Button>(R.id.button5)
        val button6 = findViewById<Button>(R.id.button6)
        val button7 = findViewById<Button>(R.id.button7)
        val button8 = findViewById<Button>(R.id.button8)
        val button9 = findViewById<Button>(R.id.button9)
        val buttonDot = findViewById<Button>(R.id.buttonDot)
        val buttonEquals = findViewById<Button>(R.id.buttonEquals)
        val buttonPlus = findViewById<Button>(R.id.buttonPlus)
        val buttonMinus = findViewById<Button>(R.id.buttonMinus)
        val buttonMultiply = findViewById<Button>(R.id.buttonMultiply)
        val buttonDivide = findViewById<Button>(R.id.buttonDivide)
        val buttonAC = findViewById<Button>(R.id.buttonAC)
        val buttonBack = findViewById<Button>(R.id.buttonBack)

        val numberClickListener = View.OnClickListener { view ->
            val number = (view as Button).text.toString()

            if (resultCalculated) {
                resultTextView.text = ""
                resultCalculated = false
            }

            val currentText = resultTextView.text.toString()
            resultTextView.text = String.format(getString(R.string.button_number), currentText + number)
        }


        button0.setOnClickListener(numberClickListener)
        button1.setOnClickListener(numberClickListener)
        button2.setOnClickListener(numberClickListener)
        button3.setOnClickListener(numberClickListener)
        button4.setOnClickListener(numberClickListener)
        button5.setOnClickListener(numberClickListener)
        button6.setOnClickListener(numberClickListener)
        button7.setOnClickListener(numberClickListener)
        button8.setOnClickListener(numberClickListener)
        button9.setOnClickListener(numberClickListener)
        buttonDot.setOnClickListener(numberClickListener)

        buttonPlus.setOnClickListener {
            performOperation("+")
        }

        buttonMinus.setOnClickListener {
            performOperation("-")
        }

        buttonMultiply.setOnClickListener {
            performOperation("*")
        }

        buttonDivide.setOnClickListener {
            performOperation("/")
        }

        buttonEquals.setOnClickListener {
            calculateResult()
        }

        buttonAC.setOnClickListener {
            clear()
        }

        buttonBack.setOnClickListener {
            backspace()
        }
    }

    private fun performOperation(operator: String) {
        val input = resultTextView.text.toString()
        if (input.isNotEmpty()) {
            this.operator = operator
            operand1 = input.toDouble()
            resultTextView.text = ""
        }
    }

    private fun calculateResult() {
        val input = resultTextView.text.toString()
        if (input.isNotEmpty() && operator != null) {
            operand2 = input.toDouble()
            val result = when (operator) {
                "+" -> operand1 + operand2
                "-" -> operand1 - operand2
                "*" -> operand1 * operand2
                "/" -> operand1 / operand2
                else -> 0.0
            }

            // Comprobar si el resultado es un número entero
            if (result == result.toLong().toDouble()) {
                // Mostrar el resultado como un entero
                resultTextView.text = result.toLong().toString()
            } else {
                // Mostrar el resultado con decimales
                resultTextView.text = result.toString()
            }
            resultCalculated = true
        }
    }

    private fun clear() {
        resultTextView.text = ""
        operator = null
        operand1 = 0.0
        operand2 = 0.0
        resultCalculated = false
    }

    private fun backspace() {
        val currentText = resultTextView.text.toString()
        if (currentText.isNotEmpty()) {
            resultTextView.text = currentText.substring(0, currentText.length - 1)
        }
    }
}
