package com.example.week11_prac

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.week11_prac.databinding.ItemDialogLayoutBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ItemDialog(private var clickedItemPos : Int) :BottomSheetDialogFragment(){
    private lateinit var binding : ItemDialogLayoutBinding

    private val viewModel : MyViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ItemDialogLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(clickedItemPos >=0){
            binding.editTextTextPersonName.setText(viewModel.getItem(clickedItemPos).name)
            binding.editTextTextPersonName2.setText(viewModel.getItem(clickedItemPos).address)
        }

        binding.button.setOnClickListener {
            val item = Item(binding.editTextTextPersonName.text.toString(), binding.editTextTextPersonName2.text.toString())
            if(clickedItemPos < 0)
                viewModel.addItem(item)
            else{
                viewModel.updateItem(item, clickedItemPos)
            }
            dismiss()
        }
    }
}