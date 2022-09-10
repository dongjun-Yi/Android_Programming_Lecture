package com.example.week6_hw

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.week6_hw.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            when(binding.radioGroup.checkedRadioButtonId){
                R.id.radioDog->{
                    binding.textView.text=binding.radioDog.text
                }
                R.id.radioCat->{
                    binding.textView.text=binding.radioCat.text
                }
            }
        }
    }
}