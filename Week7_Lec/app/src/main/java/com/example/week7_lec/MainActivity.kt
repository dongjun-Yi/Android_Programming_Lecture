package com.example.week7_lec

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import com.example.week7_lec.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel = ViewModelProvider(this)[MyViewModel::class.java]

        viewModel.myliveData.observe(this){
            binding.textView2.text="$it"
        }

        binding.button2.setOnClickListener {
            val a : Int = viewModel.myliveData.value ?: 0
            viewModel.myliveData.value = a+1
        }


        val result = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            it.resultCode
            val ret_num = it.data?.getIntExtra("result", 0) ?:0

            Snackbar.make(binding.root, "result code : ${it.resultCode}, result number : ${ret_num}", Snackbar.LENGTH_SHORT).show()
        }

        binding.button.setOnClickListener {
            val intent =Intent(this, SecondActivity::class.java)
            //startActivity(intent)
            intent.putExtra("num",10)
            result.launch(intent)
        }


    }

    override fun onStop() {
        super.onStop()
        println("OnStop")
    }

    override fun onStart() {
        super.onStart()
        println("OnStart")
    }

    override fun onDestroy() {
        super.onDestroy()
        println("OnDestroy")
    }

}