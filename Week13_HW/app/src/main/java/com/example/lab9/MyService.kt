package com.example.lab9

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import kotlinx.coroutines.*

class MyService : Service() {
    var tickCount : Int = 0

    private val binder = LocalBinder()

    inner class LocalBinder : Binder(){
        fun getService() = this@MyService
    }

    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        val data1 = intent?.getIntExtra("init",-1)

        tickCount=data1!!
        val scope = CoroutineScope(Dispatchers.Default)
        scope.launch {
            for (i in 1..10) {
                tickCount++
                delay(1000)
            }
            stopSelf(startId)
        }
        return super.onStartCommand(intent, flags, startId)
    }
}