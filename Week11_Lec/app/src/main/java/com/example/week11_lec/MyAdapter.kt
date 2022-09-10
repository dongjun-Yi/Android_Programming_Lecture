package com.example.week11_lec

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.week11_lec.databinding.ItemLayoutBinding

class MyAdapter(private val viewModel : MyViewModel): RecyclerView.Adapter<MyAdapter.ViewHolder>(){

    inner class ViewHolder(private val binding:ItemLayoutBinding):RecyclerView.ViewHolder(binding.root){
        fun setContents(pos:Int){
            val item = viewModel.getItem(pos)
            binding.textView.text=item.name
            binding.textView2.text=item.name2
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder { //parent는 recyclerview
        val layoutInflater= LayoutInflater.from(parent.context)
        val binding = ItemLayoutBinding.inflate(layoutInflater, parent, false)//false는 당장 activity에 붙이지 않는다.
        val viewHolder= ViewHolder(binding)
        binding.root.setOnLongClickListener{
            viewModel.longClickitem=viewHolder.adapterPosition
            false // false로 입력해야 시스템에 전달이 되서 컨택스트 메뉴가 생성됨
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setContents(position)
    }

    override fun getItemCount() = viewModel.itemsSize

}