package com.example.week11_prac

import android.content.ClipData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

data class Item(val name : String, val address : String)

class MyViewModel : ViewModel(){
    val itemslivedata  = MutableLiveData<ArrayList<Item>>()

    var longclickItem : Int =-1

    private val items = ArrayList<Item>()

    fun getItem(pos : Int)= items[pos]

    val ItemSize
        get() =items.size

    fun addItem(item : Item){
        items.add(item)
        itemslivedata.value = items
    }

    fun deleteItem(pos : Int){
        items.removeAt(pos)
        itemslivedata.value = items
    }

    fun updateItem(item : Item, pos :Int){
        items[pos] = item
        itemslivedata.value = items
    }
    init {
        addItem(Item("john", "test"))
        addItem(Item("james", "test2"))
    }
}