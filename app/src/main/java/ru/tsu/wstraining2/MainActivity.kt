package ru.tsu.wstraining2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import net.objecthunter.exp4j.ExpressionBuilder
import kotlin.math.pow

class MainActivity : AppCompatActivity() {

    private var canBeCleared = false
    private var isComaAlreadyUsed = false
    private val maxLength = 9

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        zeroButton.setOnClickListener{
            appendOnExpression ("0")
        }

        oneButton.setOnClickListener{
            appendOnExpression ("1")
        }

        twoButton.setOnClickListener{
            appendOnExpression ("2")
        }

        threeButton.setOnClickListener{
            appendOnExpression ("3")
        }

        fourButton.setOnClickListener{
            appendOnExpression ("4")
        }

        fiveButton.setOnClickListener{
            appendOnExpression ("5")
        }

        sixBiutton.setOnClickListener{
            appendOnExpression ("6")
        }

        sevenButton.setOnClickListener{
            appendOnExpression ("7")
        }

        eightButton.setOnClickListener{
            appendOnExpression ("8")
        }

        nineButton.setOnClickListener{
            appendOnExpression ("9")
        }

        commaButton.setOnClickListener{
            if (ifCanAddComa()) {
                appendOnExpression(".")
                isComaAlreadyUsed = true
            }
        }

        plusButton.setOnClickListener{
            if(checkIfLastIsOperand())
                appendOnExpression ("+")
        }

        minusButton.setOnClickListener{
            if(checkIfLastIsOperand())
                appendOnExpression ("-")
        }

        multiplyButton.setOnClickListener{
            if(checkIfLastIsOperand())
                appendOnExpression ("*")
        }

        devideButton.setOnClickListener{
            if(checkIfLastIsOperand())
                appendOnExpression ("/")
        }

        changeNumberSignButton.setOnClickListener {
            val expression = resultText.text
            if(expression.isEmpty())
                return@setOnClickListener
            if(expression[0] == '-') {
                val resString = expression.drop(1)
                resultText.text = ""
                resultText.append(resString)
            } else {
                var resString = "-"
                resString += resultText.text
                resultText.text = ""
                resultText.append(resString)
            }

        }

        clearButton.setOnClickListener {
            resultText.text = ""
        }

        equalButton.setOnClickListener {
            try {
                val expression = ExpressionBuilder(resultText.text.toString()).build()
                val result = expression.evaluate()
                val compare = 10.toDouble().pow(maxLength - 1)
                val checkPoint: Int = (result / compare).toInt()
                if (checkPoint <= 1) {
                    val longResult = result.toLong()
                    if (result == longResult.toDouble())
                        resultText.text = longResult.toString()
                    else
                        resultText.text = result.toString()
                } else {
                    resultText.text = "Too long"
                }
                canBeCleared = true
            } catch (e: Exception) {
                Log.d("Exception", "message : " + e.message)
            }
        }
    }


    private fun appendOnExpression (string: String) {
        if(canBeCleared) {
            resultText.text = ""
            canBeCleared = false
        }
            resultText.append(string)
    }

    private fun checkIfLastIsOperand():Boolean {
        if(resultText.text.isEmpty())
            return false
        if(canBeCleared)
            return false
        var s = resultText.text
        val lastChar = s[s.length - 1]
        if (lastChar == '+' ||
            lastChar == '-' ||
            lastChar == '*' ||
            lastChar == '/'
        ) {
            s = s.dropLast(1)
            resultText.text = s
        }
        isComaAlreadyUsed = false
        if(s.length == maxLength - 1)
            return false
        return true
    }

    private fun ifCanAddComa(): Boolean {
        return if(resultText.text.isEmpty())
            false
        else {
            val s = resultText.text
            val lastChar = s[s.length - 1]
            if (lastChar == '+' ||
                lastChar == '-' ||
                lastChar == '*' ||
                lastChar == '/'
            )
                false
            else
                !isComaAlreadyUsed
        }
    }
}