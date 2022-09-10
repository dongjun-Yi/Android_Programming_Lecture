package com.example.week11_prac

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.week11_prac.databinding.ItemLayoutBinding

class MyAdapter(private val viewModel : MyViewModel) : RecyclerView.Adapter<MyAdapter.ViewHolder>(){

    inner class ViewHolder(private val binding : ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root){
        fun setContents(pos :Int){
            val item = viewModel.getItem(pos)
            binding.textView.text = item.name
            binding.textView2.text = item.address
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding =ItemLayoutBinding.inflate(layoutInflater, parent,false)
        val viewHolder= ViewHolder(binding)

        binding.root.setOnLongClickListener {
            viewModel.longclickItem = viewHolder.adapterPosition
            false
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setContents(position)
    }

    override fun getItemCount() = viewModel.ItemSize
}