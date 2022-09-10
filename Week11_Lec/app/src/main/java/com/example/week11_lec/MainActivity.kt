package com.example.week11_lec

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.week11_lec.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    val viewModel : MyViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.floatingActionButton.setOnClickListener {
            ItemDialog().show(supportFragmentManager, "")
        }

        val adapter = MyAdapter(viewModel)
        binding.recyclerView.adapter =adapter
        binding.recyclerView.layoutManager=LinearLayoutManager(this)
        binding.recyclerView.setHasFixedSize(true)

        viewModel.itemsLiveData.observe(this){
           //adapter.notifyDataSetChanged()
            when(viewModel.itemNotifiedType){
                ItemNotify.ADD -> adapter.notifyItemInserted(viewModel.itemNotified)
                ItemNotify.UPDATE -> adapter.notifyItemChanged(viewModel.itemNotified)
                ItemNotify.DELETE -> adapter.notifyItemRemoved(viewModel.itemNotified)
            }
        }

        registerForContextMenu(binding.recyclerView)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.ctx_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.edit ->{ItemDialog().show(supportFragmentManager, "")}
            R.id.delete->{viewModel.deleteItem(viewModel.longClickitem)}
            else ->return super.onContextItemSelected(item)
        }
        return true
    }
}