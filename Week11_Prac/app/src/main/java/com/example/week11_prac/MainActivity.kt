package com.example.week11_prac

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.week11_prac.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val viewModel : MyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = MyAdapter(viewModel)
        binding.RecyclerView.adapter = adapter
        binding.RecyclerView.layoutManager = LinearLayoutManager(this)
        binding.RecyclerView.setHasFixedSize(true)

        viewModel.itemslivedata.observe(this) {
            adapter.notifyDataSetChanged()
        }
        registerForContextMenu(binding.RecyclerView)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.ctx_menu,menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.Edit -> {ItemDialog(viewModel.longclickItem).show(supportFragmentManager, "")}
            R.id.Delete ->{viewModel.deleteItem(viewModel.longclickItem)}
            else -> return super.onContextItemSelected(item)
        }
        return true
    }








}