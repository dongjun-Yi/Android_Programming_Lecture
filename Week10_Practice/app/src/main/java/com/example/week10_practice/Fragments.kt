package com.example.week10_practice


import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.example.week10_practice.databinding.FragmentBinding


class MyDialog(): DialogFragment(){
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        isCancelable=false
        return AlertDialog.Builder(requireActivity()).apply{
            setMessage("Ok-CANCEL Dialog")
            setPositiveButton("OK"){dialog, id-> println("OK")}
        }.create()
    }
}

class DateDialog(): DialogFragment(), DatePickerDialog.OnDateSetListener{

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return DatePickerDialog(requireContext(), this, 2022, 5,9)
    }

    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
        println("onDateset")
    }
}

class HomeFragment() : Fragment(R.layout.fragment){
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentBinding.bind(view)
        binding.textView.text = "HomeFragment"
    }
}

class Page1Fragment() : Fragment(R.layout.fragment){
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding =  FragmentBinding.bind(view)
        binding.textView.text = "Page1Fragment"
    }
}

class Page2Fragment() : Fragment(R.layout.fragment){
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding =  FragmentBinding.bind(view)
        binding.textView.text = "Page2Fragment"
    }
}