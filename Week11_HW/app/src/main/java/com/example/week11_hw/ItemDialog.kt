package com.example.week11_hw

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.week11_hw.databinding.ItemDialogLayoutBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ItemDialog(private var clickedItemPos : Int) : BottomSheetDialogFragment(){
    private lateinit var binding : ItemDialogLayoutBinding

    private val viewModel by activityViewModels<MyViewModel>()

    //판때기 생성
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ItemDialogLayoutBinding.inflate(inflater,container,false)
        return binding.root
    }

    //onCreateView를 생성 후 onViewCreated호출되어 판때기 위에 view들을 다루는 곳
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        if(clickedItemPos >=0){ //아이템항목이 수정되었을때
            println(" OnViewCreated clickedItemPos : $clickedItemPos")
            binding.editTextName.setText(viewModel.getItem(clickedItemPos).name)
            binding.editTextAddress.setText(viewModel.getItem(clickedItemPos).name2)
        }

        binding.buttonOK.setOnClickListener {
            val item = Item(binding.editTextName.text.toString(), binding.editTextAddress.text.toString())
            if(clickedItemPos <0){
                viewModel.addItem(item)
                println(" clickedItemPos < 0 : $clickedItemPos")
            }
            else{
                viewModel.updateItem(item, clickedItemPos)
                println(" clickedItemPos > 0: $clickedItemPos")
            }
            dismiss()
        }
    }
}