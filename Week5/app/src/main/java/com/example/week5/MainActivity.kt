package com.example.week5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.test_layout)

        val btn = findViewById<Button>(R.id.button)

        btn.setOnClickListener {
            Snackbar.make(it,"Clicked",Snackbar.LENGTH_SHORT).show()
        }
    }
}