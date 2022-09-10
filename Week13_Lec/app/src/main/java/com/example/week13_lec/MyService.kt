package com.example.week13_lec

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import kotlinx.coroutines.*

class MyService : Service() {
    private val binder = LocalBinder()

    inner class LocalBinder : Binder(){
        fun getService() = this@MyService
    }

    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        val scope = CoroutineScope(Dispatchers.Default)
        scope.launch {
            for (i in 1..10) {
                delay(1000)
                println("#")
            }
        }
        scope.launch {
            for (i in 1..10) {
                delay(1000)
                println("$")
            }
        }

        /*runBlocking { //얘는 메인스레드에서 실행함
            for (i in 1..10) {
                delay(1000)
                println("#")
            }
        }*/
        return super.onStartCommand(intent, flags, startId)
    }
}