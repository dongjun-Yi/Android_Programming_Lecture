package com.example.week7_hw


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import com.example.week7_hw.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import java.lang.Integer.parseInt

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val result =registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            it.resultCode
            val ret_num = it.data?.getIntExtra("result",0) ?:0
            println("$ret_num")
            binding.editText.setText("$ret_num")
        }

        binding.button.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            var num = parseInt(binding.editText.text.toString())
            intent.putExtra("num", num)
            result.launch(intent)
        }
    }
}