package com.example.week11_hw

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.week11_hw.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val viewModel : MyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.floatingActionButton.setOnClickListener {
            ItemDialog(-1).show(supportFragmentManager, "")
        }

        val adapter = MyAdapter(viewModel)
        binding.recyclerView.adapter =adapter
        binding.recyclerView.layoutManager= LinearLayoutManager(this)
        binding.recyclerView.setHasFixedSize(true) //recyclerView의 크기 변경이 일정하다는 것을 사용자의 입력으로 확인한다.

        //데이터에 변화가 있을때 viewModel을 통해 알림
        viewModel.itemsLiveData.observe(this){
            //추가,삭제, 수정할때 화면에 있는 모든 뷰가 다 바꾸는 거라 비효휼적
            //adapter.notifyDataSetChanged()

            //변경된 내용은 add,update,delete이다.
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
            R.id.edit ->{ItemDialog(viewModel.longClickitem).show(supportFragmentManager, "")}
            R.id.delete->{viewModel.deleteItem(viewModel.longClickitem)}
            else ->return super.onContextItemSelected(item)
        }
        return true
    }
}