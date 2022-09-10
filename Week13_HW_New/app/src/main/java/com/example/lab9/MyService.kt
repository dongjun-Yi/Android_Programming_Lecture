package com.example.lab9

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MyService : Service() {
    var tickCount : Int = 0

    private val binder = LocalBinder()

    //접속하는 Activity에서 서비스를 추출하기 위해 사용하는 객체
    inner class LocalBinder : Binder(){
        fun getService() = this@MyService
    }

    //외부에서 서비스에 접속하면 호출되는 메서드
    //IBinder는 onBind메서드를 가지는 인터페이스
    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    //서비스가 가동될 때 호출되는 메서드
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