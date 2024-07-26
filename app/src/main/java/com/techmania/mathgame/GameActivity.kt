package com.techmania.mathgame

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Locale
import kotlin.random.Random

class GameActivity : AppCompatActivity() {

    lateinit var textscore: TextView
    lateinit var textlife: TextView
    lateinit var textTime: TextView

    lateinit var questions: TextView
    lateinit var edittextAnswer: EditText
    lateinit var buttonOk: Button
    lateinit var buttonNext: Button

    var correctAnswer = 0
    var score = 0
    var life = 3
    var time = 30

    lateinit var timer: CountDownTimer
    private val startTimerInMillis: Long = 30000
    var timeLeftInMillis: Long = startTimerInMillis

    private var operation = "Addition" // Default operation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_game)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val toolbar: Toolbar = findViewById(R.id.topAppBar)
        setSupportActionBar(toolbar)

        // Get the operation type from the Intent
        operation = intent.getStringExtra("Operation") ?: "Addition"
        toolbar.title = operation

        // Enable the Up button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        textscore = findViewById(R.id.textViewScore)
        textlife = findViewById(R.id.textViewLife)
        textTime = findViewById(R.id.textViewTime)

        questions = findViewById(R.id.textViewQuestion)
        edittextAnswer = findViewById(R.id.editTextAnswer)
        buttonOk = findViewById(R.id.buttonOk)
        buttonNext = findViewById(R.id.buttonNext)

        gameContinue()

        buttonOk.setOnClickListener {

            val input = edittextAnswer.text.toString()

            if(input == " ")
            {
                Toast.makeText(this@GameActivity, "Please Write an Answer or Click the Next Button", Toast.LENGTH_LONG).show()
            }
            else
            {
                val userAnswer = input.toInt()

                if(userAnswer == correctAnswer)
                {
                    score = score + 10
                    questions.text = "Correct Answer!!"
                    textscore.text = score.toString()
                    PauseTimer()
                    resetTime()
                    edittextAnswer.setText("")

                }
                else
                {
                    life = life - 1
                    score = score - 5
                    questions.text = "Wrong Answer!!"
                    textscore.text = score.toString()
                    textlife.text = life.toString()
                    PauseTimer()
                    resetTime()
                    edittextAnswer.setText("")

                }

            }

        }

        buttonNext.setOnClickListener {

            PauseTimer()
            resetTime()
            updateText()
            gameContinue()
            edittextAnswer.setText("")


            if(life <= 0)
            {
                Toast.makeText(this@GameActivity, "Game Over", Toast.LENGTH_LONG).show()
                val intent = Intent(this@GameActivity, ResultActivity::class.java)
                intent.putExtra("score", textscore.text.toString().toInt())
                startActivity(intent)
                finish()
            }
            else
            {

                gameContinue()

            }


        }


    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    fun gameContinue()
    {
        val number1 = Random.nextInt(0, 100)
        val number2 = Random.nextInt(0, 10)

        when (operation) {
            "Addition" -> {
                questions.text = "$number1 + $number2"
                correctAnswer = number1 + number2
            }
            "Subtraction" -> {
                questions.text = "$number1 - $number2"
                correctAnswer = number1 - number2
            }
            "Multiplication" -> {
                questions.text = "$number1 * $number2"
                correctAnswer = number1 * number2
            }
            "Division" -> {
                if (number2 != 0) {
                    questions.text = "$number1 / $number2"
                    correctAnswer = number1 / number2
                } else {
                    gameContinue() // regenerate question if division by zero
                }
            }
        }

        startTimer()
    }

    fun startTimer()
    {
        timer = object : CountDownTimer(timeLeftInMillis,1000)
        {
            override fun onTick(millisUntilFinished: Long) {
                timeLeftInMillis = millisUntilFinished
                updateText()
            }

            override fun onFinish() {
                PauseTimer()
                resetTime()
                updateText()

                questions.text = "Time is up!!"

            }

        }.start()
    }

    fun PauseTimer()
    {
        timer.cancel()
    }

    fun updateText()
    {
        val remainingTime = (timeLeftInMillis / 1000).toInt()
        textTime.text = String.format(Locale.getDefault(), "%02d", remainingTime)
    }

    fun resetTime()
    {
        timeLeftInMillis = startTimerInMillis
        updateText()
    }

}
