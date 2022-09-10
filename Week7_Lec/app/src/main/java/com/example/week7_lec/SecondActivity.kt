package com.example.week7_lec

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.week7_lec.databinding.ActivityMainBinding
import com.example.week7_lec.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val num = intent?.getIntExtra("num",0) ?: 0
        binding.textView.text = "$num"

        setResult(0, Intent().putExtra("result", num*num))

    }

    override fun onStop() {
        super.onStop()
        println("second -OnStop")
    }

    override fun onStart() {
        super.onStart()
        println("second -OnStart")
    }

    override fun onDestroy() {
        super.onDestroy()
        println("second -OnDestroy")
    }
}