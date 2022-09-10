package com.example.week14_hw

import android.Manifest
import android.content.*
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.week14_hw.databinding.ActivityMainBinding

import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

data class Item(val name : String, val image: Bitmap)

enum class ItemNotify{
    ADD, UPDATE,DELETE
}

class MyViewModel : ViewModel() {
    val itemsLiveData = MutableLiveData<ArrayList<Item>>()

    private val items = ArrayList<Item>()

    //var longClickitem : Int = -1
    var itemNotified:Int = -1

    var itemNotifiedType =ItemNotify.ADD


    fun getItem(pos : Int)=items[pos]
    val itemsSize
        get() =items.size


    fun addItem(item : Item){
        itemNotifiedType = ItemNotify.ADD
        itemNotified = itemsSize
        items.add(item)
        itemsLiveData.value = items //itemsLiveData의 내용을 통째로 바꿔야지만 observe가 알게 된다. (한 원소 만 바꾼다고 알려지는 게 아님)
    }

    fun updateItem(item : Item, pos : Int){
        itemNotifiedType = ItemNotify.UPDATE
        itemNotified = pos
        items[pos] = item
        itemsLiveData.value = items
    }

    fun deleteItem(pos:Int){
        itemNotifiedType = ItemNotify.DELETE
        itemNotified = pos
        items.removeAt(pos)
        itemsLiveData.value = items
    }
}

class MainActivity : AppCompatActivity() {
    private val viewModel : MyViewModel by viewModels()
    private val broadcastReceiver = MyBroadcastReceiver()
    private val binding by lazy{ ActivityMainBinding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val adapter = MyAdapter(viewModel)
        binding.recyclerView.adapter =adapter
        binding.recyclerView.layoutManager= LinearLayoutManager(this)
        binding.recyclerView.setHasFixedSize(true) //recyclerView의 크기 변경이 일정하다는 것을 사용자의 입력으로 확인한다.
        viewModel.itemsLiveData.observe(this){
            adapter.notifyDataSetChanged()
        }

        if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED){
            val launcher = registerForActivityResult(ActivityResultContracts .RequestPermission()){

            }
            launcher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }

        val collection =if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
            MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
        else
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI

        val project = arrayOf(MediaStore.Images.ImageColumns._ID, MediaStore.Images.ImageColumns.TITLE)
        val cursor =contentResolver.query(collection, project,null,null, null)
        cursor?.apply {
            val idCol = getColumnIndex(MediaStore.Images.ImageColumns._ID)
            val idTitle = getColumnIndex(MediaStore.Images.ImageColumns.TITLE)

            while(moveToNext()){
                val contentUri = ContentUris.withAppendedId(
                collection,
                getLong(idCol)
            )
                val id = getString(idTitle)
                val bitmap = contentResolver.openInputStream(contentUri)?.use {
                    BitmapFactory.decodeStream(it)
                }
                println("id : $id, bitmap: $bitmap")

                if (bitmap != null) {
                   viewModel.addItem(Item(id,bitmap))
                }
            }
            close()
        }
    }

    override fun onStart() {
        super.onStart()
        val ifilter = IntentFilter()
        ifilter.addAction(ACTION_MY_BROADCAST)
        ifilter.addAction("android.provider.Telephony.SMS_RECEIVED")
        registerReceiver(broadcastReceiver, ifilter)
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(broadcastReceiver)
    }

    inner class MyBroadcastReceiver: BroadcastReceiver(){
        override fun onReceive(p0: Context?, p1: Intent?) {
            when(p1?.action){
                ACTION_MY_BROADCAST ->{
                    binding.textViewBroadcast.text=ACTION_MY_BROADCAST
                }
            }
        }
    }
    companion object{
        const val ACTION_MY_BROADCAST = "ACTION_MY_BROADCAST"
    }
}