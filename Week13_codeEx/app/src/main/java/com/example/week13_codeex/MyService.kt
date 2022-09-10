package com.example.week13_codeex

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import kotlinx.coroutines.*

class MyService : Service() {
    var startedCount = 0
        private set

    private val binder = LocalBinder()

    inner class LocalBinder : Binder(){
        fun getService()=this@MyService
    }


    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    override fun onCreate() {
        super.onCreate()
        println("MyService:onCreate")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { // Android 8.0
            createNotificationChannel()
        }
    }

    //서비스가 한번 생성되고 난 후 불려지면 onStartCommand만 계속 불려저 startedCount를 불릴때마다 증가시켜 메인에서 눌린 횟수의 정보를 화면에 출력
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        println("MyService:onStartCommand $startId")
        startedCount++

        startForeground(notificationID, createNotification())

        CoroutineScope(Dispatchers.IO + Job()).apply {
            launch {
                for (i in 1..10) {
                    println("in service $startId#$i")
                    updateNotification(notificationID, createNotification(i*10))
                    delay(1000)
                }
                stopSelf(startId)
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private val channelID = "default"
    private val notificationID = 1


    //만약 O버전보다 높다면 아래 함수 실행
    @RequiresApi(Build.VERSION_CODES.O)
    //채널생성
    private fun createNotificationChannel() {
        //id, 채널 이름, 채널의 중요도
        val channel = NotificationChannel(channelID, "default channel",
            NotificationManager.IMPORTANCE_DEFAULT)
        //채널의 부가 설명
        channel.description = "description text of this channel."
        //채널 생성
        NotificationManagerCompat.from(this).createNotificationChannel(channel)
    }

    private fun updateNotification(id: Int, notification: Notification) {
        //NotificationMangerCompat객체를 가져와 notify를 해주면 알림을 띄어줌. id와 모양을 넣어줌
        NotificationManagerCompat.from(this).notify(id, notification)
    }

    //알림생성
    private fun createNotification(progress: Int = 0) = NotificationCompat.Builder(this, channelID)
        .setContentTitle("Downloading")
        .setContentText("Downloading a file from a cloud")
        .setSmallIcon(R.drawable.ic_launcher_background)
        .setOnlyAlertOnce(true)  // importance 에 따라 알림 소리가 날 때, 처음에만 소리나게 함
        .setProgress(100, progress, false)
        .build()
}