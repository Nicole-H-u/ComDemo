package com.example.comdemo.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.comdemo.R

class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        val testTextView = findViewById<TextView>(R.id.testText)
        val testString = intent.getStringExtra("textString")
        testTextView.text = testString
    }

    init {
    }

}