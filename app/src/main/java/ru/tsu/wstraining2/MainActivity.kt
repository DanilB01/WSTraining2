package ru.tsu.wstraining2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.constraintlayout.widget.Group
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

        fun Group.setAllOnClickListener(listener: View.OnClickListener?) {
            referencedIds.forEach { id ->
                rootView.findViewById<View>(id).setOnClickListener(listener)
            }
        }

        numberButtons.setAllOnClickListener(View.OnClickListener {v ->
            val button = v as Button
                appendOnExpression(button.text.toString())
        })



        commaButton.setOnClickListener{
            if (ifCanAddComa()) {
                appendOnExpression(".")
                isComaAlreadyUsed = true
            }
        }

        operationButtons.setAllOnClickListener(View.OnClickListener {v ->
            val button = v as Button
            if(checkIfLastIsOperand())
                appendOnExpression (button.text.toString())
        })

        changeNumberSignButton.setOnClickListener {
            val expression = resultText.text
            if(expression.isEmpty())
                return@setOnClickListener
            if(expression[0] == '-') {
                val resString = expression.drop(1)
                resultText.text = resString
            } else {
                resultText.text = "-${resultText.text}"
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
                    resultText.text = getString(R.string.tooLong)
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