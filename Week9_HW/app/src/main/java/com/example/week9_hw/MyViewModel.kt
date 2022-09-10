package com.example.week9_hw

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyViewModel : ViewModel() {
    val testLiveData = MutableLiveData<Int>()
    init {
        testLiveData.value=0
    }

    fun increase() {
        testLiveData.value= (testLiveData.value ?: 0) +1
    }
}