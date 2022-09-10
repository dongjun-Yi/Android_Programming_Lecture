package com.example.week10_lec

import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.example.week10_lec.databinding.FragmentLayoutBinding

class MyDialogFragment : DialogFragment(){
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext()).apply{
            setTitle("Dialog Title")
            setPositiveButton("Ok"){dialog, id->
                println("OK")
            }
        }.create()
    }
}

class HomeFragment : Fragment(R.layout.fragment_layout){
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentLayoutBinding.bind(view)
        binding.textView2.text = "HomeFragment"
    }
}

class Page1Fragment : Fragment(R.layout.fragment_layout){
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentLayoutBinding.bind(view)
        binding.textView2.text = "Page1Fragment"
    }
}

class Page2Fragment : Fragment(R.layout.fragment_layout){
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentLayoutBinding.bind(view)
        binding.textView2.text = "Page2Fragment"
    }
}