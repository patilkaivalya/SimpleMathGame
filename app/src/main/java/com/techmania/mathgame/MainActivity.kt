package com.techmania.mathgame

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.appbar.MaterialToolbar

class MainActivity : AppCompatActivity() {

    lateinit var addition: Button
    lateinit var subtraction: Button
    lateinit var multiplication: Button
    lateinit var division: Button
    lateinit var appbar: MaterialToolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        appbar = findViewById(R.id.topAppBar)
        setSupportActionBar(appbar)

        addition = findViewById(R.id.buttonaddition)
        addition.setOnClickListener {
            val intent = Intent(this@MainActivity, GameActivity::class.java)
            intent.putExtra("Operation", "Addition")
            startActivity(intent)
        }
        subtraction = findViewById(R.id.buttonsubtraction)
        subtraction.setOnClickListener {
            val intent = Intent(this@MainActivity, GameActivity::class.java)
            intent.putExtra("Operation", "Subtraction")
            startActivity(intent)
        }
        multiplication = findViewById(R.id.buttonmultiplication)
        multiplication.setOnClickListener {
            val intent = Intent(this@MainActivity, GameActivity::class.java)
            intent.putExtra("Operation", "Multiplication")
            startActivity(intent)
        }
        division = findViewById(R.id.buttondivision)
        division.setOnClickListener {
            val intent = Intent(this@MainActivity, GameActivity::class.java)
            intent.putExtra("Operation", "Division")
            startActivity(intent)
        }
    }
}
