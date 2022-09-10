package com.example.week11_hw


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
data class Item(val name : String, val name2: String)

enum class ItemNotify{
    ADD, UPDATE,DELETE
}

class MyViewModel : ViewModel() {
    val itemsLiveData = MutableLiveData<ArrayList<Item>>()

    private val items = ArrayList<Item>()

    var longClickitem : Int = -1
    var itemNotified:Int = -1

    var itemNotifiedType =ItemNotify.ADD

    init {
        addItem(Item("john", "test"))
        addItem(Item("james", "test2"))
    }

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