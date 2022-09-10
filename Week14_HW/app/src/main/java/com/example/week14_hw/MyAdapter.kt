package com.example.week14_hw

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.week14_hw.databinding.ItemBinding

class MyAdapter(private val viewModel : MyViewModel): RecyclerView.Adapter<MyAdapter.ViewHolder>(){

    inner class ViewHolder(private val binding : ItemBinding):RecyclerView.ViewHolder(binding.root) {
        fun setContents(pos:Int){
            val item = viewModel.getItem(pos)
            binding.textView.text=item.name
            binding.imageView.setImageBitmap(item.image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter.ViewHolder {
        val layoutInflater= LayoutInflater.from(parent.context)
        val binding =ItemBinding.inflate(layoutInflater,parent, false)
        val viewHolder = ViewHolder(binding)
        return viewHolder
    }

    override fun onBindViewHolder(holder: MyAdapter.ViewHolder, position: Int) {
        holder.setContents(position)
    }

    override fun getItemCount() = viewModel.itemsSize
}