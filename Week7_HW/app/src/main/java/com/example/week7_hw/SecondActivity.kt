package com.example.week7_hw

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.inflate
import androidx.lifecycle.ViewModelProvider
import com.example.week7_hw.databinding.ActivitySecondBinding
import java.lang.Integer.parseInt
import kotlin.properties.Delegates

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val viewModel = ViewModelProvider(this)[MyViewModel::class.java]

        val num = intent?.getIntExtra("num", 0) ?: 0
        var a: Int = viewModel.myliveData.value ?: num

        viewModel.myliveData.observe(this) {
            binding.textView.text = "$it"
            //var b : Int = parseInt(binding.textView.text.toString())
            var b = it
            setResult(0, Intent().putExtra("result", b))
        }

        binding.textView.text = "$a"

        binding.buttonInc.setOnClickListener {
            a++
            viewModel.myliveData.value = a
        }
        binding.buttonDec.setOnClickListener {
            a--
            viewModel.myliveData.value = a
        }
    }

}