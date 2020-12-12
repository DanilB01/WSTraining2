package ru.tsu.wstraining2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {

    private var canBeCleared = false

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
            appendOnExpression (".")
        }

        plusButton.setOnClickListener{
            appendOnExpression ("+")
        }

        minusButton.setOnClickListener{
            appendOnExpression ("-")
        }

        multiplyButton.setOnClickListener{
            appendOnExpression ("*")
        }

        devideButton.setOnClickListener{
            appendOnExpression ("/")
        }

        clearButton.setOnClickListener {
            resultText.text = ""
        }

        equalButton.setOnClickListener {
            try {
                val expression = ExpressionBuilder(resultText.text.toString()).build()
                val result = expression.evaluate()
                val longResult = result.toLong()
                if (result == longResult.toDouble())
                    resultText.text = longResult.toString()
                else
                    resultText.text = result.toString()
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
}