package com.example.week7_hw

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyViewModel : ViewModel() {
    val myliveData = MutableLiveData<Int>()
}