package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private var result: TextView? = null
    private var lastNumeric: Boolean = false
    private var lastDot: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar!!.hide()

        result = findViewById<TextView>(R.id.result)
    }

    fun onDigit(view: View) {
        result?.append((view as Button).text)
        lastNumeric = true
        lastDot = false
    }

    fun onClear(view: View) {
        result?.text = ""
    }

    fun onDecimalPoint(view: View) {
        if (lastNumeric && !lastDot){
            result?.append(".")
            lastNumeric = false
            lastDot = true
        }
    }

    fun onOperator(view: View){
        result?.text?.let {
            if(lastNumeric && !onAddedOperator(it.toString())){
                result?.append((view as Button).text)
                lastNumeric = false
                lastDot = false
            }
        }

    }
    private fun onAddedOperator(value: String) : Boolean {
        return if(value.startsWith("-")) {
             false
        } else {
            value.contains("/") || value.contains("+")|| value.contains("x") || value.contains("-")
        }
    }

    fun onEqual(view: View){
        if(lastNumeric) {
            var resultValue = result?.text.toString()
            var prefix = ""
            try {
                if (resultValue.startsWith("-")) {
                    prefix = ("-")
                    resultValue = resultValue.substring(1)
                }
                if (resultValue.contains("-")) {
                    val splitValue = resultValue.split('-')
                    var one = splitValue[0]
                    var two = splitValue[1]
                    if (prefix.isNotEmpty()) {
                        one = prefix + one
                    }
                    result?.text = removeZeroAfterDot((one.toDouble() - two.toDouble()).toString())
                } else if (resultValue.contains("+")) {
                    val splitValue = resultValue.split('+')
                    var one = splitValue[0]
                    var two = splitValue[1]
                    if (prefix.isNotEmpty()) {
                        one = prefix + one
                    }
                    result?.text = removeZeroAfterDot((one.toDouble() + two.toDouble()).toString())

                } else if (resultValue.contains("/")) {
                    val splitValue = resultValue.split('/')
                    var one = splitValue[0]
                    var two = splitValue[1]
                    if (prefix.isNotEmpty()) {
                        one = prefix + one
                    }
                    result?.text = removeZeroAfterDot((one.toDouble() / two.toDouble()).toString())
                } else if (resultValue.contains("x")) {
                    val splitValue = resultValue.split('x')
                    var one = splitValue[0]
                    var two = splitValue[1]
                    if (prefix.isNotEmpty()) {
                        one = prefix + one
                    }
                    result?.text = removeZeroAfterDot((one.toDouble() * two.toDouble()).toString())
                }
            } catch (e: ArithmeticException) {
                e.printStackTrace()
            }
        }
    }

    private fun removeZeroAfterDot(newResult: String) :String{
        var value = newResult
        if(newResult.contains(".0"))
            value = newResult.substring(0, newResult.length - 2)
        return value
    }

}