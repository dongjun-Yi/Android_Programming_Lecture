package com.example.week9_practice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.week9_practice.databinding.Fragment1Binding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}

class Fragment1 : Fragment(R.layout.fragment1){
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = Fragment1Binding.bind(view)

        binding.button.setOnClickListener {
            findNavController().navigate(R.id.action_fragment1_to_framgent2)
        }
    }
}

class Framgent2 : Fragment(R.layout.fragment2){
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}