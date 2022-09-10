package com.example.week10_hw

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.example.week10_hw.databinding.FragmentLayoutBinding

class MyDialogFragment : DialogFragment(){
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext()).apply{
            setTitle("1871183")
            setMessage("이동준")
            setPositiveButton("OK"){dialog, id->
                dismiss()
            }
        }.create()
    }
}
class HomeFragment : Fragment(R.layout.fragment_layout){
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentLayoutBinding.bind(view)
        binding.textView.text = "HomeFragment"
    }
}

class Page1Fragment : Fragment(R.layout.fragment_layout){
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentLayoutBinding.bind(view)
        binding.textView.text = "Page1Fragment"
    }
}

class Page2Fragment : Fragment(R.layout.fragment_layout){
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentLayoutBinding.bind(view)
        binding.textView.text = "Page2Fragment"
    }
}
